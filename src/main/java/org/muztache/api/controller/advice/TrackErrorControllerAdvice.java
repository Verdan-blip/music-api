package org.muztache.api.controller.advice;

import org.muztache.api.dto.common.response.ShortErrorResponse;
import org.muztache.api.exceptions.track.TrackNotFoundException;
import org.muztache.api.exceptions.track.TrackUploadingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TrackErrorControllerAdvice {

    @ResponseBody
    @ExceptionHandler({TrackNotFoundException.class, TrackUploadingException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ShortErrorResponse onTrackException(RuntimeException exception) {
        return new ShortErrorResponse(exception.getMessage());
    }
}
