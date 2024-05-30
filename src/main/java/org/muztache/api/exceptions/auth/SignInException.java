package org.muztache.api.exceptions.auth;

public class SignInException extends RuntimeException {

    public SignInException(String message) {
        super(message);
    }
}
