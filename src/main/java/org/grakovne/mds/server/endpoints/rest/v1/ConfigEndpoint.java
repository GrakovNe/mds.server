package org.grakovne.mds.server.endpoints.rest.v1;

import org.grakovne.mds.server.endpoints.rest.v1.support.ApiResponse;
import org.grakovne.mds.server.utils.ConfigurationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Configuration endpoint.
 */

@RestController
@RequestMapping("/api/v1/configuration")
public class ConfigEndpoint {

    @Autowired
    private ConfigurationUtils configurationUtils;

    /**
     * Returns list of custom configs in application.yml file.
     *
     * @return response with configs
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")

    public ApiResponse<ConfigurationUtils> getConfigurations() {
        return new ApiResponse<>(configurationUtils);
    }
}
