package org.muztache.api.exceptions.common;

public class CoverUploadingException extends  RuntimeException {

    public CoverUploadingException() {
        super();
    }

    public CoverUploadingException(String message) {
        super(message);
    }

    public CoverUploadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoverUploadingException(Throwable cause) {
        super(cause);
    }

    protected CoverUploadingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
