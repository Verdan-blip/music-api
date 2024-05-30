package org.muztache.api.dto.track.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class TrackUploadRequest {

    @NotBlank
    private String title;

    private List<Long> authorIds = new ArrayList<>();

    @NotNull
    private MultipartFile cover;

    private MultipartFile smallCover = null;

    private String lyrics = null;

    @NotNull
    private String genre;

    @NotNull
    private MultipartFile audioFile;

    private MultipartFile clipFile;

    private Long clipStart = null;

    private Long clipEnd = null;
}
