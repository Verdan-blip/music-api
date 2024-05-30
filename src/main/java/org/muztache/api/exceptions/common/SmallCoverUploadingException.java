package org.muztache.api.exceptions.common;

public class SmallCoverUploadingException extends  RuntimeException {

    public SmallCoverUploadingException() {
        super();
    }

    public SmallCoverUploadingException(String message) {
        super(message);
    }

    public SmallCoverUploadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public SmallCoverUploadingException(Throwable cause) {
        super(cause);
    }

    protected SmallCoverUploadingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
