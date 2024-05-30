package org.muztache.api.controller.rest.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.muztache.api.dto.security.request.UpdatePasswordRequest;
import org.muztache.api.exceptions.user.UserNotFoundException;
import org.muztache.api.model.User;
import org.muztache.api.service.EmailSenderService;
import org.muztache.api.service.SecurityService;
import org.muztache.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/v1/password")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PasswordRestController {

    private static String PASSWORD_RESET_SUBJECT = "Password recovery";
    private static String PASSWORD_RESET_TEXT_FORMAT = "Here is your recovery code: %s. " +
            "Don't share it with anyone!";

    private final UserService userService;

    private final SecurityService securityService;

    private final EmailSenderService emailSenderService;

    @PostMapping("/reset")
    @ResponseStatus(HttpStatus.OK)
    public void resetPassword(@RequestParam("email") String email) {
        User user = userService.getByEmail(email);

        if (user == null) {
            throw new UserNotFoundException();
        }

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);

        emailSenderService.send(email, PASSWORD_RESET_SUBJECT, PASSWORD_RESET_TEXT_FORMAT.formatted(token));
    }

    @PostMapping("/change")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@RequestBody @Valid UpdatePasswordRequest updatePasswordRequest) {
        User user = securityService.validatePasswordResetToken(
                updatePasswordRequest.getToken()
        );
        userService.saveNewPassword(user, updatePasswordRequest.getNewPassword());
    }
}
