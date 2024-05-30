package org.muztache.api.dto.security.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdatePasswordRequest {

    @NotNull
    private final String token;

    @NotNull
    @Size(min = 8, max = 255, message = "Password length must be at least 8 and no more than 255 characters")
    private final String newPassword;
}
