package com.alexisindustries.library.auth.config.advice;

import com.alexisindustries.library.auth.exception.EntityAlreadyExistsException;
import com.alexisindustries.library.auth.exception.EntityNotFoundException;
import com.alexisindustries.library.auth.exception.ExceptionMessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerExceptionHandler {
    @ExceptionHandler({
            NullPointerException.class,
            IllegalStateException.class,
            IllegalArgumentException.class,
    })
    public ResponseEntity<Object> handleOtherException(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionMessageResponse("An error occurred", "Bad Request"));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionMessageResponse(e.getMessage(), "Bad credentials"));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionMessageResponse(e.getMessage(), "Entity not found"));
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<Object> handleEntityAlreadyExistsException(EntityAlreadyExistsException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionMessageResponse(e.getMessage(), "Entity already exists"));
    }
}
