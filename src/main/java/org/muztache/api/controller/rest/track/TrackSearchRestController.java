package org.muztache.api.controller.rest.track;

import lombok.RequiredArgsConstructor;
import org.muztache.api.dto.track.response.TrackCollectionResponse;
import org.muztache.api.dto.track.response.TrackDetailsResponse;
import org.muztache.api.service.TrackSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/api/v1/tracks")
public class TrackSearchRestController {

    private final TrackSearchService trackSearchService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TrackDetailsResponse getTrack(@PathVariable("id") Long trackId) {
        return trackSearchService.getTrackById(trackId);
    }

    @GetMapping(value = "/charts", produces = MediaType.APPLICATION_JSON_VALUE)
    public TrackCollectionResponse getCharts(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer count
    ) {
        return trackSearchService.getTopCharts();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public TrackCollectionResponse getTrackCollection(
            @RequestParam String keys,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer count
    ) {
        return trackSearchService.getTracksByKeyWords(
                keys.split("\\+"),
                PageRequest.of(page, count)
        );
    }
}
