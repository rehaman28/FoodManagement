package com.dm.Exception;

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

    @ExceptionHandler(DeliverAssignmentNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleDeliverAssignmentNotFoundException(
            DeliverAssignmentNotFoundException ex,
            HttpServletRequest httpServletRequest) {

        ErrorResponseDto response = buildErrorResponse(
                HttpStatus.NOT_FOUND,
                "DeliveryAssignment Not Found",
                ex.getMessage(),
                httpServletRequest.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(DeliveryPersonNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleDeliveryPersonNotFoundException(
                DeliveryPersonNotFoundException ex,
                HttpServletRequest httpServletRequest) {

        ErrorResponseDto response = buildErrorResponse(
                HttpStatus.NOT_FOUND,
                "DeliveryPerson Not Found",
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
    @ExceptionHandler(InvalidDeliveryStatusTransitionException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidDeliveryStatusTransitionException(
                InvalidDeliveryStatusTransitionException ex,
                HttpServletRequest httpServletRequest) {

        ErrorResponseDto response = buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Invalid delivery status transition",
                ex.getMessage(),
                httpServletRequest.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(DeliveryAgentNotAvailableException.class)
    public ResponseEntity<ErrorResponseDto> handleDeliveryAgentNotAvailableException(
                DeliveryAgentNotAvailableException ex,
                HttpServletRequest httpServletRequest) {

        ErrorResponseDto response = buildErrorResponse(
                HttpStatus.NOT_FOUND,
                "Delivery Agent not available",
                ex.getMessage(),
                httpServletRequest.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
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

     private ErrorResponseDto buildErrorResponse(
            HttpStatus status,
            String error,
            String message,
            String path) {
        return buildErrorResponse(status, error, message, path, null);
    }
}