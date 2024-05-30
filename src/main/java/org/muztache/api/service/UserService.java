package org.muztache.api.service;

import lombok.RequiredArgsConstructor;
import org.muztache.api.coverter.UserToUserResponseConverter;
import org.muztache.api.dto.user.response.UserCollectionResponse;
import org.muztache.api.dto.user.response.UserResponse;
import org.muztache.api.exceptions.user.UserWithSuchEmailAlreadyExistsException;
import org.muztache.api.exceptions.user.UserWithSuchLoginAlreadyExistsException;
import org.muztache.api.model.PasswordResetToken;
import org.muztache.api.model.User;
import org.muztache.api.repository.PasswordTokenRepository;
import org.muztache.api.repository.UserRepository;
import org.muztache.api.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordTokenRepository passwordTokenRepository;

    private final UserToUserResponseConverter userToUserResponseConverter;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return UserDetailsImpl.fromUser(getByLogin(login));
    }

    public User getByLogin(String login) {
        return userRepository.findUserByLogin(login).orElse(null);
    }

    public User getByEmail(String email) {
        return userRepository.findUserByEmail(email).orElse(null);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User create(User user) {
        if (userRepository.existsUserByLogin(user.getLogin())) {
            throw new UserWithSuchLoginAlreadyExistsException(user.getLogin());
        }
        if (userRepository.existsUserByEmail(user.getEmail())) {
            throw new UserWithSuchEmailAlreadyExistsException(user.getEmail());
        }
        return save(user);
    }

    public User getCurrentUser() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByLogin(login);
    }

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiryDate(Date.from(Instant.now().plusMillis(1_000 * 60 * 60 * 24)))
                .build();
        passwordTokenRepository.save(passwordResetToken);
    }

    public void saveNewPassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public UserCollectionResponse searchUsers(String[] keywords) {
        final Set<UserResponse> userResponses = new HashSet<>();
        for (String key : keywords) {
            userResponses.addAll(
                    userRepository.findAllByLoginContainingIgnoreCase(key)
                            .stream()
                            .map(userToUserResponseConverter::convert)
                            .toList()
            );
        }
        return UserCollectionResponse.builder()
                .count(userResponses.size())
                .foundUsers(userResponses.stream().toList())
                .build();
    }
}
