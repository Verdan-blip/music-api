package org.muztache.api.controller.rest.profile;

import lombok.RequiredArgsConstructor;
import org.muztache.api.dto.user.request.UpdateProfileRequest;
import org.muztache.api.dto.user.response.UserProfileResponse;
import org.muztache.api.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/api/v1/profile")
public class ProfileRestController {

    private final UserProfileService userProfileService;

    @GetMapping("/me")
    public UserProfileResponse getUserProfile() {
        return userProfileService.getCurrentUserProfile();
    }


    @PostMapping(
            value = "/update",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public void updateProfile(@ModelAttribute UpdateProfileRequest request) {
        userProfileService.updateProfile(request);
    }
}
