package org.muztache.api.controller.rest.search;

import lombok.RequiredArgsConstructor;
import org.muztache.api.dto.search.SearchResponse;
import org.muztache.api.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/api/v1/search")
public class SearchRestController {

    private final SearchService searchService;

    @GetMapping
    public SearchResponse search(@RequestParam(value = "keywords") String unparsedKeys) {
        String[] keys = unparsedKeys.split("\\+");
        return searchService.searchByKeywords(keys);
    }
}
