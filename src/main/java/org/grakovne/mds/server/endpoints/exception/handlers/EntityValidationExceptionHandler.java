package org.grakovne.mds.server.endpoints.exception.handlers;

import org.grakovne.mds.server.endpoints.support.ApiResponse;
import org.grakovne.mds.server.exceptons.EntityValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring exception handler.
 */

@RestController
@ControllerAdvice
public class EntityValidationExceptionHandler {

    /**
     * Handles EntityException when validation failed.
     *
     * @param ex exception object
     * @return status response
     */

    @ExceptionHandler(EntityValidationException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiResponse entityAlreadyExistsFoundHandler(EntityValidationException ex) {
        return new ApiResponse(ex.getMessage());
    }
}
