package org.muztache.api.controller.rest.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.muztache.api.dto.auth.request.JwtRefreshRequest;
import org.muztache.api.dto.auth.request.SignInRequest;
import org.muztache.api.dto.auth.request.SignUpRequest;
import org.muztache.api.dto.auth.response.JwtAuthenticationResponse;
import org.muztache.api.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationRestController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/signin")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }

    @PostMapping("/token")
    @ResponseStatus(HttpStatus.OK)
    public JwtAuthenticationResponse getNewAccessToken(@RequestBody @Valid JwtRefreshRequest request) {
        return authenticationService.getAccessToken(request);
    }

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public JwtAuthenticationResponse refresh(@RequestBody @Valid JwtRefreshRequest request) {
        return authenticationService.refresh(request);
    }
}
