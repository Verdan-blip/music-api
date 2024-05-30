package org.muztache.api.dto.auth.request;

import lombok.Data;

@Data
public class JwtRefreshRequest {

    private String refreshToken;
}
