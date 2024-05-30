package org.muztache.api.exceptions.user;

public class UserWithSuchLoginAlreadyExistsException extends RuntimeException {

    public UserWithSuchLoginAlreadyExistsException(String login) {
        super(String.format("User with login %s already exists", login));
    }
}
