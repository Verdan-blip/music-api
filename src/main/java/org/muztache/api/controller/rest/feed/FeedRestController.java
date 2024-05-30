package org.muztache.api.controller.rest.feed;

import lombok.RequiredArgsConstructor;
import org.muztache.api.dto.feed.response.FeedResponse;
import org.muztache.api.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/api/v1/feed")
public class FeedRestController {

    private final FeedService feedService;

    @GetMapping
    public FeedResponse getCharts() {
        return feedService.getFeed();
    }
}
