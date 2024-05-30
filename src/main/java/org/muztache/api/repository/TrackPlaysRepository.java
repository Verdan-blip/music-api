package org.muztache.api.repository;

import org.muztache.api.model.TrackPlay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackPlaysRepository extends JpaRepository<TrackPlay, Long> {

}
