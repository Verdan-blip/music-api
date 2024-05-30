package org.muztache.api.exceptions.playlist;

public class PlaylistUploadingException extends RuntimeException {

    public PlaylistUploadingException() {
    }

    public PlaylistUploadingException(String message) {
        super(message);
    }

    public PlaylistUploadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlaylistUploadingException(Throwable cause) {
        super(cause);
    }

    public PlaylistUploadingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
