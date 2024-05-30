package org.muztache.api.dto.playlist.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@RequiredArgsConstructor
public class PlaylistUploadRequest {

    @NotBlank
    private final String title;

    @NotNull
    private final MultipartFile cover;
}
