package org.muztache.api.repository;

import org.muztache.api.model.Track;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {

    Optional<Track> findMusicById(Long id);

    List<Track> findAllByReleaseDateBetween(Date startDate, Date endDate);

    List<Track> findAllByTitleContainingIgnoreCase(String title);
}
