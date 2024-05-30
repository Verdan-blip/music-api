package org.muztache.api.dto.auth.response;


import lombok.*;

@Getter
@AllArgsConstructor
public class JwtAuthenticationResponse {

    private final String accessToken;

    private final String refreshToken;

    private final Long expiresIn;

    private final String type = "Bearer";
}
