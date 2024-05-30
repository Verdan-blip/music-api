package org.muztache.api.controller.advice;

import org.muztache.api.dto.common.response.ShortErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AnyErrorControllerAdvice {

    @ResponseBody
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ShortErrorResponse onAuthenticationException(Exception exception) {
        return new ShortErrorResponse(exception.getMessage());
    }
}
