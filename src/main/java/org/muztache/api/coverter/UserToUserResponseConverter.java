package org.muztache.api.coverter;

import org.muztache.api.dto.user.response.UserResponse;
import org.muztache.api.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserResponseConverter implements Converter<User, UserResponse> {

    @Override
    public UserResponse convert(User source) {
        return UserResponse.builder()
                .userId(source.getId())
                .login(source.getLogin())
                .avatarUri(source.getAvatarUri())
                .build();
    }
}
