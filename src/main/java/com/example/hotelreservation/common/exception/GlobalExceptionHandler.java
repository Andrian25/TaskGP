package com.example.hotelreservation.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(final NotFoundException exception)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody(exception.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(final IllegalArgumentException exception)
    {
        return ResponseEntity.badRequest().body(errorBody(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(final MethodArgumentNotValidException exception)
    {
        final String message = exception.getBindingResult().getAllErrors().isEmpty()
                ? "Validation failed"
                : exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(errorBody(message));
    }

    private Map<String, String> errorBody(final String message)
    {
        final Map<String, String> response = new HashMap<>();
        response.put("error", message);
        return response;
    }
}
