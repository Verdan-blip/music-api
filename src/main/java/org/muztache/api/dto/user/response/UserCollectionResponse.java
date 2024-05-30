package org.muztache.api.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class UserCollectionResponse {

    private final Integer count;

    private final List<UserResponse> foundUsers;
}
