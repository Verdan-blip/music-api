package org.muztache.api.exceptions.playlist;

public class PlaylistNotFoundException extends RuntimeException {

    public PlaylistNotFoundException(Long playlistId) {
        super(String.format("Playlist with id %d not found", playlistId));
    }
}
