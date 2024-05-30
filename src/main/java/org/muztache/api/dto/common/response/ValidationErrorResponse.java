package org.muztache.api.dto.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.muztache.api.util.Violation;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ValidationErrorResponse {

    private final List<Violation> violations;
}
