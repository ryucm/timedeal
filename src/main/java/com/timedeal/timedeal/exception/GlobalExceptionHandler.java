package com.timedeal.timedeal.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exceptions.class)
    public ResponseEntity<ErrorResponse> handleException(Exceptions e) {
        log.error(String.format("Error : %s", e.toString()));
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(new ErrorResponse(e.getErrorCode()));
    }

    @ExceptionHandler(HttpSessionRequiredException.class)
    public ResponseEntity<ErrorResponse> sessionHandler(Exceptions e) {
        log.error(String.format("Error : %s", e.toString()));
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(new ErrorResponse(e.getErrorCode()));
    }
}
