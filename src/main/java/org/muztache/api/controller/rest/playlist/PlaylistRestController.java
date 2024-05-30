package org.muztache.api.controller.rest.playlist;

import lombok.RequiredArgsConstructor;
import org.muztache.api.dto.playlist.request.PlaylistUploadRequest;
import org.muztache.api.service.PlaylistUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/v1/playlists")
public class PlaylistRestController {

    private final PlaylistUploadService playlistUploadService;

    @PostMapping(
            value = "/new",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void insertPlaylist(@ModelAttribute PlaylistUploadRequest playlistUploadRequest) {
        playlistUploadService.insert(playlistUploadRequest);
    }

    @PostMapping(
            value = "/update",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public void updatePlaylist(@ModelAttribute PlaylistUpdateRequest playlistUpdateRequest) {
        playlistUploadService.update(playlistUpdateRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePlaylist(@PathVariable(name = "id") Long playlistId) {
        playlistUploadService.delete(playlistId);
    }
}
