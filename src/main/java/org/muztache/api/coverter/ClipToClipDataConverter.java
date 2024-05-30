package org.muztache.api.coverter;

import org.muztache.api.dto.track.response.ClipData;
import org.muztache.api.model.Clip;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ClipToClipDataConverter implements Converter<Clip, ClipData> {

    @Override
    public ClipData convert(Clip source) {
        return ClipData.builder()
                .clipFileUri(source.getFileUri())
                .clipStart(source.getStartMs())
                .clipEnd(source.getEndMs())
                .build();
    }
}
