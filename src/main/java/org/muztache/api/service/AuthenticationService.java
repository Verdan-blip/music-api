package org.muztache.api.service;

import io.jsonwebtoken.Claims;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.muztache.api.dto.auth.request.JwtRefreshRequest;
import org.muztache.api.dto.auth.request.SignInRequest;
import org.muztache.api.dto.auth.request.SignUpRequest;
import org.muztache.api.dto.auth.response.JwtAuthenticationResponse;
import org.muztache.api.exceptions.auth.*;
import org.muztache.api.exceptions.user.UserNotFoundException;
import org.muztache.api.model.Role;
import org.muztache.api.model.User;
import org.muztache.api.repository.RoleRepository;
import org.muztache.api.security.JwtService;
import org.muztache.api.security.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;

    private final Map<String, String> refreshStorage = new HashMap<>();

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final RoleRepository roleRepository;

    private static final String AVATAR_PLACEHOLDER_URI =
            "https://res.cloudinary.com/dq3jdp5f0/image/upload/v1717109924/avatars/avatar_placeholder.jpg";

    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        Set<Role> roles = Set.of(
                roleRepository.findRoleByName("USER").orElseThrow(() ->
                        new RoleNotFoundException("USER")
                )
        );

        User user = User.builder()
                .login(request.getLogin())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .avatarUri(AVATAR_PLACEHOLDER_URI)
                .roles(roles)
                .build();

        userService.create(user);

        UserDetails userDetails = UserDetailsImpl.fromUser(user);

        final String accessJwtToken = jwtService.generateAccessToken(userDetails);
        final String refreshJwtToken = jwtService.generateRefreshToken(userDetails);
        final Long expiresIn = jwtService.getAccessExpirationMs();

        return new JwtAuthenticationResponse(
                accessJwtToken, refreshJwtToken, expiresIn
        );
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getLogin(),
                    request.getPassword()
            ));

            UserDetails userDetails = userService.loadUserByUsername(request.getLogin());

            final String accessJwtToken = jwtService.generateAccessToken(userDetails);
            final String refreshJwtToken = jwtService.generateRefreshToken(userDetails);
            final Long expiresIn = jwtService.getAccessExpirationMs();

            refreshStorage.put(userDetails.getUsername(), refreshJwtToken);

            return new JwtAuthenticationResponse(
                    accessJwtToken,
                    refreshJwtToken,
                    expiresIn
            );

        } catch (BadCredentialsException exception) {
            throw new IncorrectPasswordException();
        } catch (UsernameNotFoundException | InternalAuthenticationServiceException exception) {
            throw new UserNotFoundException(request.getLogin());
        } catch (AuthenticationException exception) {
            throw new SignInException(exception.getMessage());
        }
    }

    public JwtAuthenticationResponse refresh(@NotNull JwtRefreshRequest request) {

        String refreshToken = request.getRefreshToken();

        if (jwtService.isRefreshTokenValid(refreshToken)) {

            final Claims claims = jwtService.extractAllRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);

            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {

                final User user = userService.getByLogin(login);
                final UserDetails userDetails = UserDetailsImpl.fromUser(user);

                final String accessToken = jwtService.generateAccessToken(userDetails);
                final String newRefreshToken = jwtService.generateRefreshToken(userDetails);

                final Long expiresIn = jwtService.getAccessExpirationMs();

                refreshStorage.put(user.getLogin(), newRefreshToken);

                return new JwtAuthenticationResponse(
                        accessToken, newRefreshToken, expiresIn
                );
            }
        }
        throw new RefreshTokenNotValidException();
    }

    public JwtAuthenticationResponse getAccessToken(@NonNull JwtRefreshRequest request) {

        String refreshToken = request.getRefreshToken();

        if (jwtService.isRefreshTokenValid(refreshToken)) {
            final Claims claims = jwtService.extractAllRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {

                final User user = userService.getByLogin(login);
                final UserDetails userDetails = UserDetailsImpl.fromUser(user);

                final String accessToken = jwtService.generateAccessToken(userDetails);
                final Long expiresIn = jwtService.getAccessExpirationMs();

                return new JwtAuthenticationResponse(
                        accessToken,
                        refreshToken,
                        expiresIn
                );
            }
        }
        throw new RefreshTokenNotValidException();
    }
}
