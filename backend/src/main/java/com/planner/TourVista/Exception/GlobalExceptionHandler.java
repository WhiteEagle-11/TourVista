package com.planner.TourVista.Exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(
        MethodArgumentNotValidException exception
    ){
        Map<String, String> errors= new HashMap<>();

        exception.getBindingResult()
        .getFieldErrors()
        .forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(TripNotFoundException.class)
public ResponseEntity<Map<String, String>> handleTripNotFound(
        TripNotFoundException exception) {

    Map<String, String> error = new HashMap<>();
    error.put("error", exception.getMessage());

    return ResponseEntity.status(404).body(error);
}

@ExceptionHandler(ExternalApiException.class)
public ResponseEntity<Map<String, String>> handleExternalApiException(
        ExternalApiException ex) {

    return ResponseEntity
            .status(HttpStatus.BAD_GATEWAY)
            .body(Map.of("error", ex.getMessage()));
}

    
}
