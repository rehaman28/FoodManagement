package com.rm.Exception;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
     @ExceptionHandler(RestaurnatNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleProductNotFoundException(RestaurnatNotFoundException ex) {
        Map<String, Object> body = Map.of(
            "timestamp", LocalDateTime.now().toString(),
            "status", HttpStatus.NOT_FOUND.value(),
            "error", "Not Found",
            "message", ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> body = Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "error", "Internal Server Error",
                "message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
    
    // @ExceptionHandler(OrderNotFoundException.class)
    // public ResponseEntity<Map<String, Object>> handleOrderNotFoundException(OrderNotFoundException ex) {
    //     Map<String, Object> body = Map.of(
    //             "timestamp", LocalDateTime.now().toString(),
    //             "status", HttpStatus.NOT_FOUND.value(),
    //             "error", "Not Found Error",
    //             "message", ex.getMessage());
    //     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    // }

}
