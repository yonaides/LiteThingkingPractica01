package com.progamming.lite.thinking.tareapractica.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ClientNotFoundException.class})
    public ResponseEntity<Object> handleStudentNotValidException(ClientNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }
}
