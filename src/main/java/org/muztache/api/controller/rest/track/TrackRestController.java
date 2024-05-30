package org.muztache.api.controller.rest.track;

import lombok.RequiredArgsConstructor;
import org.muztache.api.dto.track.request.TrackUpdateRequest;
import org.muztache.api.dto.track.request.TrackUploadRequest;
import org.muztache.api.service.TrackUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/api/v1/tracks")
public class TrackRestController {

    private final TrackUploadService trackUploadService;

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @ResponseStatus(value = HttpStatus.OK)
    public void uploadTrack(@ModelAttribute TrackUploadRequest trackUploadRequest) {
        trackUploadService.upload(trackUploadRequest);
    }

    @PostMapping(
            value = "/update",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(value = HttpStatus.OK)
    public void updateTrack(@ModelAttribute TrackUpdateRequest trackUpdateRequest) {
        trackUploadService.update(trackUpdateRequest);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteTrack(@PathVariable(name = "id") Long trackId) {
        trackUploadService.delete(trackId);
    }
}
