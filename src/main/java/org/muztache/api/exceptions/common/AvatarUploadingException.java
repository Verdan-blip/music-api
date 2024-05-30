package org.muztache.api.exceptions.common;

public class AvatarUploadingException extends RuntimeException {

    public AvatarUploadingException() {
        super("Failed to upload avatar due to internal server error");
    }
}
