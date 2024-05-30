package org.muztache.api.exceptions.track;

public class TrackUploadingException extends RuntimeException {
    public TrackUploadingException() {
        super();
    }

    public TrackUploadingException(String message) {
        super(message);
    }

    public TrackUploadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public TrackUploadingException(Throwable cause) {
        super(cause);
    }

    protected TrackUploadingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
