package org.grakovne.mds.server.endpoints.rest.v1;

import org.grakovne.mds.server.endpoints.rest.v1.support.ApiResponse;
import org.grakovne.mds.server.entity.User;
import org.grakovne.mds.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    /**
     * Finds users.
     *
     * @param pageNumber page number
     * @return page with entities
     */

    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Page<User>> findUsers(
        @RequestParam(required = false, defaultValue = "0") Integer pageNumber) {

        return new ApiResponse<>(userService.findUsers(pageNumber));
    }

    /**
     * Returns user data of logged user.
     *
     * @param user user principal
     * @return User object
     */

    @RequestMapping(value = "me", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public ApiResponse<User> getMyUserDetails(@AuthenticationPrincipal User user) {
        return new ApiResponse<>(user);
    }

    /**
     * Saves new user in db.
     *
     * @param userDTO user dto with username and password
     * @return User object
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ApiResponse<User> createUser(@RequestBody User userDTO) {
        User user = userService.createUser(userDTO);
        return new ApiResponse<>(user);
    }
}
