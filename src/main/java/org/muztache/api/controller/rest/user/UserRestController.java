package org.muztache.api.controller.rest.user;

import lombok.RequiredArgsConstructor;
import org.muztache.api.dto.user.response.UserCollectionResponse;
import org.muztache.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {

    private final UserService userService;

    @GetMapping
    public UserCollectionResponse searchUsers(
            @RequestParam String keys
    ) {
        return userService.searchUsers(keys.split("\\+"));
    }
}
