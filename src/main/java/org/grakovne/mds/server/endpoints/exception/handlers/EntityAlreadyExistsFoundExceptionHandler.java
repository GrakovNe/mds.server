package org.grakovne.mds.server.endpoints.exception.handlers;

import org.grakovne.mds.server.endpoints.support.ApiResponse;
import org.grakovne.mds.server.exceptons.EntityAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class EntityAlreadyExistsFoundExceptionHandler {

    @ExceptionHandler(EntityAlreadyExistException.class)
    @ResponseStatus(value = HttpStatus.FOUND)
    public ApiResponse entityAlreadyExistsFoundHandler(EntityAlreadyExistException ex) {
        return new ApiResponse(ex.getMessage());
    }
}
