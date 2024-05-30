package org.muztache.api.dto.user.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@RequiredArgsConstructor
public class UpdateProfileRequest {

    private final Short age;

    private final String name;

    private final String lastname;

    private final MultipartFile avatar;
}
