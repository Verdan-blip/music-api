package org.muztache.api.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.muztache.api.coverter.TrackToTrackDetailsResponseConverter;
import org.muztache.api.coverter.TrackToTrackResponseConverter;
import org.muztache.api.coverter.UserToUserResponseConverter;
import org.muztache.api.dto.track.response.TrackCollectionResponse;
import org.muztache.api.dto.track.response.TrackDetailsResponse;
import org.muztache.api.dto.track.response.TrackResponse;
import org.muztache.api.exceptions.track.TrackNotFoundException;
import org.muztache.api.model.Track;
import org.muztache.api.model.TrackPlay;
import org.muztache.api.model.User;
import org.muztache.api.repository.TrackPlaysRepository;
import org.muztache.api.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TrackSearchService {

    private final EntityManager entityManager;

    private final TrackRepository trackRepository;

    private final TrackPlaysRepository trackPlaysRepository;

    private final UserService userService;


    private final UserToUserResponseConverter userToUserResponseConverter;

    private final TrackToTrackResponseConverter trackToTrackResponseConverter;

    private final TrackToTrackDetailsResponseConverter trackToTrackDetailsResponseConverter;

    @Transactional
    public TrackDetailsResponse getTrackById(Long trackId) {
        Optional<Track> optionalTrack = trackRepository.findMusicById(trackId);
        if (optionalTrack.isPresent()) {
            Track track = optionalTrack.get();

            User user = userService.getCurrentUser();

            if (user != null) {
                trackPlaysRepository.save(
                        TrackPlay.builder()
                                .user(userService.getCurrentUser())
                                .track(track)
                                .playingTime(Timestamp.valueOf(LocalDateTime.now()))
                                .build()
                );
            }
            return trackToTrackDetailsResponseConverter.convert(track);
        } else {
            throw new TrackNotFoundException(trackId);
        }
    }

    public TrackCollectionResponse getTopCharts() {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);

        calendar.set(currentYear, currentMonth, 1);
        Date firstDayOfMonth = new Date(calendar.getTimeInMillis());

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date lastDayOfMonth = new Date(calendar.getTimeInMillis());

        List<Track> tracks = trackRepository
                .findAllByReleaseDateBetween(firstDayOfMonth, lastDayOfMonth);

        List<TrackResponse> trackResponses = tracks
                .stream()
                .map(trackToTrackResponseConverter::convert)
                .toList();

        return new TrackCollectionResponse(
                trackResponses.size(), trackResponses
        );
    }

    public TrackCollectionResponse getTracksByKeyWords(
            String[] keyWords, Pageable pageable
    ) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Track> criteriaQuery = criteriaBuilder.createQuery(Track.class);
        Root<Track> root = criteriaQuery.from(Track.class);

        List<Predicate> predicates = new ArrayList<>();
        for (String keyWord : keyWords) {

            final String regex = "%" + keyWord + "%";

            //Searching for matches in titles
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("title")), regex
            ));

            //Searching for matches in lyrics
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("lyrics")), regex
            ));
        }

        //Executing query
        criteriaQuery.select(criteriaQuery.from(Track.class))
                .where(criteriaBuilder.or(predicates.toArray(new Predicate[0])))
                .orderBy(criteriaBuilder.asc(root.get("title")));

        List<Track> tracks = entityManager.createQuery(criteriaQuery)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults((int) pageable.getPageSize())
                .getResultList();

        //Converting into DTO
        List<TrackResponse> trackDetailsResponse = new ArrayList<>();
        for (Track track : tracks) {
            trackDetailsResponse.add(trackToTrackResponseConverter.convert(track));
        }

        return new TrackCollectionResponse(trackDetailsResponse.size(), trackDetailsResponse);
    }
}
