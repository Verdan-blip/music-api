package org.muztache.api.dto.playlist.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PlaylistCollectionResponse {

    private final Integer count;

    private final List<PlaylistResponse> playlists;
}
