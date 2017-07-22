package org.grakovne.mds.server.endpoints.rest.v1.exception.handlers;

import org.grakovne.mds.server.endpoints.rest.v1.support.ApiResponse;
import org.grakovne.mds.server.exceptons.EntityNotFoundException;
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
public class EntityNotFoundExceptionHandler {

    /**
     * Handles EntityNotFoundException when no entity in db.
     *
     * @param ex exception object
     * @return status response
     */

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiResponse entityNotFoundHandler(EntityNotFoundException ex) {
        return new ApiResponse(ex.getMessage());
    }
}
