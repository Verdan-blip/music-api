package org.muztache.api.exceptions.user;

import org.muztache.api.exceptions.auth.SignInException;

public class UserNotFoundException extends SignInException {

    public UserNotFoundException() {
        super("User not found");
    }

    public UserNotFoundException(String userName) {
        super(String.format("User with login %s not found", userName));
    }

    public UserNotFoundException(Long userId) {
        super(String.format("User with id %d not found", userId));
    }
}
