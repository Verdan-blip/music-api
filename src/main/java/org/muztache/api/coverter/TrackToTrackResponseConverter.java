package org.muztache.api.coverter;

import lombok.RequiredArgsConstructor;
import org.muztache.api.dto.track.response.TrackResponse;
import org.muztache.api.model.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class TrackToTrackResponseConverter implements Converter<Track, TrackResponse> {

    private final UserToUserResponseConverter userToUserResponseConverter;

    @Override
    public TrackResponse convert(Track source) {
        return new TrackResponse(
                source.getId(),
                source.getTitle(),
                source.getUsers()
                        .stream()
                        .map(userToUserResponseConverter::convert)
                        .toList(),
                source.getSmallCoverUri()
        );
    }
}
