package org.muztache.api.controller.rest.playlist;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class PlaylistUpdateRequest {

    private final Long id;

    private final String title;

    private final MultipartFile avatar;

    private final List<Long> trackIdsToAdd;

    private final List<Long> trackIdsToRemove;
}
