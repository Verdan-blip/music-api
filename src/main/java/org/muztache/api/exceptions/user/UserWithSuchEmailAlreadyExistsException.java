package org.muztache.api.exceptions.user;

public class UserWithSuchEmailAlreadyExistsException extends RuntimeException {

    public UserWithSuchEmailAlreadyExistsException(String email) {
        super(String.format("User with email %s already exists", email));
    }
}
