package org.muztache.api.dto.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ShortErrorResponse {

    private final String message;

    @Override
    public String toString() {
        return String.format("{ message: \"%s\" }", message);
    }
}
