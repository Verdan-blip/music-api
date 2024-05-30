package org.muztache.api.controller.rest.playlist;

import lombok.AllArgsConstructor;
import org.muztache.api.dto.playlist.response.PlaylistDetailsResponse;
import org.muztache.api.service.PlaylistSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/v1/playlists")
public class PlaylistSearchRestController {

    private final PlaylistSearchService playlistSearchService;

    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public PlaylistDetailsResponse getPlaylistById(@PathVariable(name = "id") Long playlistId) {
        return playlistSearchService.getPlaylistById(playlistId);
    }
}
