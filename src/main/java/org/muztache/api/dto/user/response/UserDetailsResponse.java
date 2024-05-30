package org.muztache.api.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.muztache.api.dto.playlist.response.PlaylistResponse;
import org.muztache.api.dto.track.response.TrackResponse;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class UserDetailsResponse {

    private final Long userId;

    private final String login;

    private final String avatarUri;

    private final List<TrackResponse> tracks;

    private final List<PlaylistResponse> playlists;
}
