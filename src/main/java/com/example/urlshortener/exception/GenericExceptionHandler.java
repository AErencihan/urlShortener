package com.example.urlshortener.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<?> handler(GlobalException ex){
        Map<String, Object> errors = new HashMap<>();
        errors.put("message", ex.getMessage());
        return ResponseEntity
                .status(ex.getHttpStatus() != null ? ex.getHttpStatus() : HttpStatus.BAD_REQUEST)
                .body(errors);


    }
}
