package org.muztache.api.coverter;

import lombok.RequiredArgsConstructor;
import org.muztache.api.dto.playlist.response.PlaylistResponse;
import org.muztache.api.model.Playlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class PlaylistToPlaylistResponseConverter implements Converter<Playlist, PlaylistResponse> {

    private final UserToUserResponseConverter userToUserResponseConverter;

    private final TrackToTrackResponseConverter trackToTrackResponseConverter;

    @Override
    public PlaylistResponse convert(Playlist source) {
        return new PlaylistResponse(
                source.getId(),
                source.getTitle(),
                source.getCoverUrl(),
                userToUserResponseConverter.convert(source.getUser()),
                source.getTracks()
                        .stream()
                        .map(trackToTrackResponseConverter::convert)
                        .toList()
        );
    }
}
