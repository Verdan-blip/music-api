package org.muztache.api.dto.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema
public class SignInRequest {

    @Schema(description = "Login", example = "John")
    @Size(min = 5, max = 50, message = "Login must contain from 5 to 50 characters")
    @NotBlank(message = "Login can't be empty")
    private String login;

    @Schema(description = "Password", example = "my_1secret1_password")
    @Size(min = 8, max = 255, message = "Password length must be at least 8 and no more than 255 characters")
    private String password;
}
