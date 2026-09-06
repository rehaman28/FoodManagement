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

        ErrorResponseDto response = buildErrorResponse(
                HttpStatus.NOT_FOUND,
                "Restaurant Not Found",
                ex.getMessage(),
                httpServletRequest.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleItemNotFoundException(ItemNotFoundException ex,
            HttpServletRequest httpServletRequest) {

        ErrorResponseDto response = buildErrorResponse(
                HttpStatus.NOT_FOUND,
                "Item Not Found",
                ex.getMessage(),
                httpServletRequest.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception ex,
            HttpServletRequest request) {

        ErrorResponseDto response = buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                "An unexpected error occurred",
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    private ErrorResponseDto buildErrorResponse(
            HttpStatus status,
            String error,
            String message,
            String path) {
        return buildErrorResponse(status, error, message, path, null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (existing, replacement) -> existing));

        ErrorResponseDto response = buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Validation Failed",
                "One or more request fields are invalid",
                request.getRequestURI(),
                errors);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    private ErrorResponseDto buildErrorResponse(
            HttpStatus status,
            String error,
            String message,
            String path,
            Map<String, String> validationErrors) {
        return ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(error)
                .message(message)
                .path(path)
                .validationErrors(validationErrors)
                .build();
    }

    // @ExceptionHandler(MethodArgumentNotValidException.class)
    // public ResponseEntity<Map<String, Object>> handleValidationException(
    // MethodArgumentNotValidException ex) {

    // Map<String, String> errors =
    // ex.getBindingResult()
    // .getFieldErrors()
    // .stream()
    // .collect(Collectors.toMap(
    // FieldError::getField,
    // FieldError::getDefaultMessage,
    // (existing, replacement) -> existing
    // ));

    // Map<String, Object> body = Map.of(
    // "timestamp", LocalDateTime.now().toString(),
    // "status", HttpStatus.BAD_REQUEST.value(),
    // "error", "Validation Failed",
    // "errors", errors
    // );

    // return ResponseEntity
    // .status(HttpStatus.BAD_REQUEST)
    // .body(body);
    // }

    // @ExceptionHandler(RestaurantNotFoundException.class)
    // public ResponseEntity<Map<String, Object>> handleRestaurantNotFoundException(
    // RestaurantNotFoundException ex){
    // Generic Response
    // Map<String, Object> body = Map.of(
    // "timestamp", LocalDateTime.now().toString(),
    // "status", HttpStatus.NOT_FOUND.value(),
    // "error", "Not Found",
    // "message", ex.getMessage()
    // );
    // }

    // @ExceptionHandler(OrderNotFoundException.class)
    // public ResponseEntity<Map<String, Object>>
    // handleOrderNotFoundException(OrderNotFoundException ex) {
    // Map<String, Object> body = Map.of(
    // "timestamp", LocalDateTime.now().toString(),
    // "status", HttpStatus.NOT_FOUND.value(),
    // "error", "Not Found Error",
    // "message", ex.getMessage());
    // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    // }

    // @ExceptionHandler(ItemNotFoundException.class)
    // public ResponseEntity<Map<String, Object>>
    // handleItemNotFoundException(ItemNotFoundException ex) {
    // Map<String, Object> body = Map.of(
    // "timestamp", LocalDateTime.now().toString(),
    // "status", HttpStatus.NOT_FOUND.value(),
    // "error", "Not Found",
    // "message", ex.getMessage());
    // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    // }

    // @ExceptionHandler(Exception.class)
    // public ResponseEntity<Map<String, Object>> handleGenericException(Exception
    // ex) {
    // Map<String, Object> body = Map.of(
    // "timestamp", LocalDateTime.now().toString(),
    // "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
    // "error", "Internal Server Error",
    // "message", ex.getMessage());
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    // }

}