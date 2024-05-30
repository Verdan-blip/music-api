package org.muztache.api.dto.playlist.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.muztache.api.dto.track.response.TrackResponse;
import org.muztache.api.dto.user.response.UserResponse;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PlaylistDetailsResponse {

    private final Long playlistId;

    private final String title;

    private final String coverUri;

    private final UserResponse user;

    private final List<TrackResponse> tracks;

    private final String createdDate;

    private final Integer playsCount;
}
