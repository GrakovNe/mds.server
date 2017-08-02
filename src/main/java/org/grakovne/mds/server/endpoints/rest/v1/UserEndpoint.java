package org.grakovne.mds.server.endpoints.rest.v1;

import org.grakovne.mds.server.endpoints.rest.v1.support.ApiResponse;
import org.grakovne.mds.server.entity.User;
import org.grakovne.mds.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring user endpoint.
 */

@RestController
@RequestMapping("/api/v1/user")
public class UserEndpoint {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ApiResponse<Page<User>> findUsers(
        @RequestParam(required = false, defaultValue = "0") Integer pageNumber) {

        return new ApiResponse<>(userService.findUsers(pageNumber));
    }

    @RequestMapping(value = "me", method = RequestMethod.GET)
    public ApiResponse<User> getMyUserDetails() {
        String userName = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.loadUserByUsername(userName);

        return new ApiResponse<>(user);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ApiResponse<User> createUser(@RequestBody User userDTO) {
        User user = userService.createUser(userDTO);
        return new ApiResponse<>(user);
    }
}
