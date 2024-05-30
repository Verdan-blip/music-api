package org.muztache.api.exceptions.auth;

public class IncorrectPasswordException extends SignInException {

    public IncorrectPasswordException() {
        super("Incorrect password");
    }
}
