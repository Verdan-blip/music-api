package org.muztache.api.service;

import lombok.RequiredArgsConstructor;
import org.muztache.api.exceptions.user.UserNotFoundException;
import org.muztache.api.model.PasswordResetToken;
import org.muztache.api.model.User;
import org.muztache.api.repository.PasswordTokenRepository;
import org.muztache.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.Calendar;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityService {

    private final PasswordTokenRepository passwordTokenRepository;

    public User validatePasswordResetToken(String token) {
        Optional<PasswordResetToken> optional = passwordTokenRepository.findByToken(token);

        if (optional.isPresent()) {
            PasswordResetToken passwordResetToken = optional.get();
            if (isTokenExpired(passwordResetToken)) {
                throw new RuntimeException("Reset token was expired");
            } else {
                User user = passwordResetToken.getUser();
                passwordResetToken.setExpiryDate(Date.from(Instant.now()));
                passwordTokenRepository.save(passwordResetToken);
                return user;
            }
        } else {
            throw new UserNotFoundException();
        }
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }
}
