package org.muztache.api.controller.advice;

import org.muztache.api.dto.common.response.ShortErrorResponse;
import org.muztache.api.exceptions.user.UserWithSuchEmailAlreadyExistsException;
import org.muztache.api.exceptions.user.UserWithSuchLoginAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserErrorControllerAdvice {

    @ResponseBody
    @ExceptionHandler({
            UserWithSuchLoginAlreadyExistsException.class,
            UserWithSuchEmailAlreadyExistsException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ShortErrorResponse onUserException(RuntimeException exception) {
        return new ShortErrorResponse(exception.getMessage());
    }
}
