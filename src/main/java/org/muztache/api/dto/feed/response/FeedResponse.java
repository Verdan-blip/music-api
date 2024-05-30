package org.muztache.api.dto.feed.response;

import lombok.Data;
import org.muztache.api.dto.playlist.response.PlaylistResponse;
import org.muztache.api.dto.track.response.TrackResponse;

import java.util.List;

@Data
public class FeedResponse {

    private final List<TrackResponse> chartTracks;

    private final List<PlaylistResponse> popularPlaylists;
}
