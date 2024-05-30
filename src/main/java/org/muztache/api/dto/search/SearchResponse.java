package org.muztache.api.dto.search;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.muztache.api.dto.playlist.response.PlaylistResponse;
import org.muztache.api.dto.track.response.TrackResponse;
import org.muztache.api.dto.user.response.UserResponse;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class SearchResponse {

    private final List<TrackResponse> tracks;

    private final List<PlaylistResponse> playlists;

    private final List<UserResponse> users;
}
