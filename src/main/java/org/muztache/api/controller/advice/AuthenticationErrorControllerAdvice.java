package org.muztache.api.controller.advice;

import org.muztache.api.dto.common.response.ShortErrorResponse;
import org.muztache.api.exceptions.auth.IncorrectPasswordException;
import org.muztache.api.exceptions.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.SignatureException;

@ControllerAdvice
public class AuthenticationErrorControllerAdvice {

    @ResponseBody
    @ExceptionHandler({
            UserNotFoundException.class,
            IncorrectPasswordException.class,
            SignatureException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ShortErrorResponse onAuthenticationException(RuntimeException exception) {
        return new ShortErrorResponse(exception.getMessage());
    }
}
