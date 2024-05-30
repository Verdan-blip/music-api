package org.muztache.api.coverter;

import lombok.RequiredArgsConstructor;
import org.muztache.api.dto.track.response.ClipData;
import org.muztache.api.dto.track.response.TrackDetailsResponse;
import org.muztache.api.model.Clip;
import org.muztache.api.model.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class TrackToTrackDetailsResponseConverter implements Converter<Track, TrackDetailsResponse> {

    private final UserToUserResponseConverter userToUserResponseConverter;

    private final ClipToClipDataConverter clipToClipDataConverter;

    @Override
    public TrackDetailsResponse convert(Track source) {
        Clip clip = source.getClip();
        ClipData clipData = clip == null ? null : clipToClipDataConverter.convert(clip);
        return new TrackDetailsResponse(
                source.getId(),
                source.getTitle(),
                source.getGenre(),
                source.getTrackPlays().size(),
                source.getUsers()
                        .stream()
                        .map(userToUserResponseConverter::convert)
                        .toList(),
                source.getLyrics(),
                source.getReleaseDate().toString(),
                source.getCoverUri(),
                source.getSmallCoverUri(),
                source.getAudioFileUri(),
                clipData
        );
    }
}
