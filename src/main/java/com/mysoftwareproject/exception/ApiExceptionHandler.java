package com.mysoftwareproject.exception;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    // private final static Logger LOGGER = (Logger) LoggerFactory.getLogger(ApiExceptionHandler.class);

    // following method gets exception
    @ExceptionHandler(value = ApiRequestException.class)
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        // build exception
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST, ZonedDateTime.now(), e.getMessage());
        // send response
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);

    }

    // following method gets exception
    @ExceptionHandler(value = ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<Object> handleApiRequestException(ChangeSetPersister.NotFoundException e) {
        // build exception
        ApiException apiException = new ApiException(HttpStatus.NOT_FOUND, ZonedDateTime.now(), e.getMessage());
        // send response
        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);

    }
}


