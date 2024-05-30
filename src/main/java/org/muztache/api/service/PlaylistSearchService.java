package org.muztache.api.service;

import lombok.AllArgsConstructor;
import org.muztache.api.dto.playlist.response.PlaylistDetailsResponse;
import org.muztache.api.exceptions.playlist.PlaylistNotFoundException;
import org.muztache.api.model.Playlist;
import org.muztache.api.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PlaylistSearchService {

    private final PlaylistRepository playlistRepository;

    private PlaylistDetailsResponse playlistToPlaylistDetailsResponse(Playlist playlist) {
        return PlaylistDetailsResponse.builder()
                .playlistId(playlist.getId())
                .title(playlist.getTitle())
                .coverUri(playlist.getCoverUrl())
                .createdDate(playlist.getCreateTime().toString())
                .build();
    }

    public PlaylistDetailsResponse getPlaylistById(Long playlistId) {
        Optional<Playlist> optionalPlaylist = playlistRepository.findPlaylistById(playlistId);
        if (optionalPlaylist.isPresent()) {
            return playlistToPlaylistDetailsResponse(optionalPlaylist.get());
        } else {
            throw new PlaylistNotFoundException(playlistId);
        }
    }
}
