package org.muztache.api.controller.advice;

import org.muztache.api.dto.common.response.ShortErrorResponse;
import org.muztache.api.exceptions.playlist.PlaylistUploadingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PlaylistErrorControllerAdvice {

    @ResponseBody
    @ExceptionHandler({PlaylistUploadingException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ShortErrorResponse onPlayerException(RuntimeException exception) {
        return new ShortErrorResponse(exception.getMessage());
    }
}
