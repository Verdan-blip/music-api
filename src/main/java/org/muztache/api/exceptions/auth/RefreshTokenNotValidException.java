package org.muztache.api.exceptions.auth;

public class RefreshTokenNotValidException extends RuntimeException {

    public RefreshTokenNotValidException() {
        super("Невалидный JWT токен");
    }
}
