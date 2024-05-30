package org.muztache.api.coverter;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.muztache.api.dto.user.response.UserProfileResponse;
import org.muztache.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class UserToUserProfileResponseConverter implements Converter<User, UserProfileResponse> {

    private final TrackToTrackResponseConverter trackToTrackResponseConverter;

    private final PlaylistToPlaylistResponseConverter playlistToPlaylistResponseConverter;

    @Override
    public UserProfileResponse convert(User source) {
        return new UserProfileResponse(
                source.getId(),
                source.getLogin(),
                source.getEmail(),
                source.getAvatarUri(),
                source.getTracks()
                        .stream()
                        .map(trackToTrackResponseConverter::convert)
                        .toList(),
                source.getPlaylists()
                        .stream()
                        .map(playlistToPlaylistResponseConverter::convert)
                        .toList()
        );
    }
}
