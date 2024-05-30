package org.muztache.api.dto.track.response;

import lombok.*;
import org.muztache.api.dto.user.response.UserResponse;

import java.util.List;

@Getter @Setter
@Builder
@AllArgsConstructor
public class TrackDetailsResponse {

    private Long trackId;

    private String title;

    private String genre;

    private Integer playsCount;

    private List<UserResponse> users;

    private String lyrics;

    private String releaseDate;

    private String coverUri;

    private String smallCoverUri;

    private String audioFileUri;

    private ClipData clipData;
}
