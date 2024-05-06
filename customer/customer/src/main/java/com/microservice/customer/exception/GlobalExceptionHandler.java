package com.microservice.customer.exception;

import com.microservice.customer.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@RestControllerAdvice
public class GlobalExceptionHandler {
    LocalDateTime currentTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss", Locale.ENGLISH);


    @ExceptionHandler(UserValidation.class)
    public ResponseEntity<ApiResponse> userValidationExceptionHandler(UserValidation ex) {
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, false, LocalDateTime.now());
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }
}
