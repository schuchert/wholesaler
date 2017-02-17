package com.tradefederation.wholesaler.endpoint;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(ex);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    class ErrorResponse {
        public final String message;

        public ErrorResponse(Exception e) {
            message = e.getMessage();
        }
    }
}
