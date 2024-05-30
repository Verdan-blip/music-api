package org.muztache.api.service;

import lombok.RequiredArgsConstructor;
import org.muztache.api.coverter.PlaylistToPlaylistResponseConverter;
import org.muztache.api.coverter.TrackToTrackResponseConverter;
import org.muztache.api.dto.feed.response.FeedResponse;
import org.muztache.api.dto.search.SearchResponse;
import org.muztache.api.model.Track;
import org.muztache.api.repository.PlaylistRepository;
import org.muztache.api.repository.TrackRepository;
import org.muztache.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class FeedService {

    private final TrackRepository trackRepository;

    private final PlaylistRepository playlistRepository;

    private final TrackToTrackResponseConverter trackToTrackResponseConverter;

    private final PlaylistToPlaylistResponseConverter playlistToPlaylistResponseConverter;

    public FeedResponse getFeed() {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);

        calendar.set(currentYear, currentMonth, 1);
        Date firstDayOfMonth = new Date(calendar.getTimeInMillis());

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date lastDayOfMonth = new Date(calendar.getTimeInMillis());


        List<Track> feedTracks = trackRepository.findAllByReleaseDateBetween(
                firstDayOfMonth, lastDayOfMonth
        );

        return new FeedResponse(
                feedTracks
                        .stream()
                        .map(trackToTrackResponseConverter::convert)
                        .toList(),
                List.of()
        );
    }
}
