package org.muztache.api.exceptions.track;

public class TrackNotFoundException extends RuntimeException {

    public TrackNotFoundException(Long trackId) {
        super(String.format("Track with id %d not found", trackId));
    }
}
