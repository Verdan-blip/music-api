package org.muztache.api.dto.track.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.muztache.api.dto.user.response.UserResponse;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class TrackResponse {

    private Long trackId;

    private String title;

    private List<UserResponse> users;

    private String smallCoverUri;
}
