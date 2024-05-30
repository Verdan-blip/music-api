package org.muztache.api.dto.track.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@RequiredArgsConstructor
public class TrackUpdateRequest {

    private final Long id;

    private final String title;

    private final MultipartFile smallCover;

    private final MultipartFile cover;

    private final String lyrics;
}
