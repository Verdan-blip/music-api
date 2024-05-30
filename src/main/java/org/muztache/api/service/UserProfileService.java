package org.muztache.api.service;

import lombok.RequiredArgsConstructor;
import org.muztache.api.coverter.UserToUserProfileResponseConverter;
import org.muztache.api.dto.user.request.UpdateProfileRequest;
import org.muztache.api.dto.user.response.UserProfileResponse;
import org.muztache.api.exceptions.common.AvatarUploadingException;
import org.muztache.api.model.User;
import org.muztache.api.util.MultipartFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class UserProfileService {

    private final UserService userService;

    private final CloudinaryService cloudinaryService;

    private final UserToUserProfileResponseConverter userToUserProfileResponseConverter;

    public UserProfileResponse getCurrentUserProfile() {
        User user = userService.getCurrentUser();
        return userToUserProfileResponseConverter.convert(user);
    }

    public void updateProfile(UpdateProfileRequest request) {
        User user = userService.getCurrentUser();

        if (request.getAge() != null) {
            user.setAge(request.getAge());
        }

        if (request.getName() != null) {
            user.setName(request.getName());
        }

        if (request.getLastname() != null) {
            user.setLastname(request.getLastname());
        }

        if (request.getAvatar() != null) {
            try {
                File avatar = MultipartFileUtils.asFile(request.getAvatar());
                String avatarUri = cloudinaryService.upload(avatar, CloudinaryService.UploadType.AVATAR);
                user.setAvatarUri(avatarUri);
            } catch (IOException exception) {
                throw new AvatarUploadingException();
            }
        }

        userService.save(user);
    }
}
