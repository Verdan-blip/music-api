package org.muztache.api.dto.track.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ClipData {

    @NotNull
    private final String clipFileUri;

    @NotNull
    private final Long clipStart;

    @NotNull
    private final Long clipEnd;
}
