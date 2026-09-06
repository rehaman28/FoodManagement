package com.rm.Exception;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice 
public class GlobalExceptionHandler {

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleRestaurantNotFoundException(
            RestaurantNotFoundException ex,
            HttpServletRequest httpServletRequest) {

        ErrorResponseDto response = ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Restaurant Not Found")
                .message(ex.getMessage())
                .path(httpServletRequest.getRequestURI())
                .build();
        

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleItemNotFoundException(ItemNotFoundException ex,
        HttpServletRequest httpServletRequest) {
            
        ErrorResponseDto response = ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Item Not Found")
                .message(ex.getMessage())
                .path(httpServletRequest.getRequestURI())
                .build();
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception ex,
        HttpServletRequest request) {

        ErrorResponseDto response = ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Internal Server Error")
                .message("An unexpected error occurred")
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(
        MethodArgumentNotValidException ex) {

    Map<String, String> errors =
            ex.getBindingResult()
              .getFieldErrors()
              .stream()
              .collect(Collectors.toMap(
                  FieldError::getField,
                  FieldError::getDefaultMessage,
                  (existing, replacement) -> existing
              ));

    Map<String, Object> body = Map.of(
            "timestamp", LocalDateTime.now().toString(),
            "status", HttpStatus.BAD_REQUEST.value(),
            "error", "Validation Failed",
            "errors", errors
    );

    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(body);
}

    
    //@ExceptionHandler(RestaurantNotFoundException.class)
    // public ResponseEntity<Map<String, Object>> handleRestaurantNotFoundException(
    //         RestaurantNotFoundException ex){
    // Generic Response
        // Map<String, Object> body = Map.of(
        // "timestamp", LocalDateTime.now().toString(),
        // "status", HttpStatus.NOT_FOUND.value(),
        // "error", "Not Found",
        // "message", ex.getMessage()
        // );
    //  }  
    
    // @ExceptionHandler(OrderNotFoundException.class)
    // public ResponseEntity<Map<String, Object>> handleOrderNotFoundException(OrderNotFoundException ex) {
    //     Map<String, Object> body = Map.of(
    //             "timestamp", LocalDateTime.now().toString(),
    //             "status", HttpStatus.NOT_FOUND.value(),
    //             "error", "Not Found Error",
    //             "message", ex.getMessage());
    //     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    // }

    // @ExceptionHandler(ItemNotFoundException.class)
    // public ResponseEntity<Map<String, Object>> handleItemNotFoundException(ItemNotFoundException ex) {
    //     Map<String, Object> body = Map.of(
    //             "timestamp", LocalDateTime.now().toString(),
    //             "status", HttpStatus.NOT_FOUND.value(),
    //             "error", "Not Found",
    //             "message", ex.getMessage());
    //     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    // }

    // @ExceptionHandler(Exception.class)
    // public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
    //     Map<String, Object> body = Map.of(
    //             "timestamp", LocalDateTime.now().toString(),
    //             "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
    //             "error", "Internal Server Error",
    //             "message", ex.getMessage());
    //     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    // }

}
