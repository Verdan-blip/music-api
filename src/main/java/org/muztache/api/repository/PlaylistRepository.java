package org.muztache.api.repository;

import org.muztache.api.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    Optional<Playlist> findPlaylistById(Long id);

    List<Playlist> findAllByTitleContainingIgnoreCase(String title);
}
