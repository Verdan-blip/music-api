package org.muztache.api.dto.track.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class TrackCollectionResponse {

    private final Integer count;

    private final List<TrackResponse> tracks;
}
