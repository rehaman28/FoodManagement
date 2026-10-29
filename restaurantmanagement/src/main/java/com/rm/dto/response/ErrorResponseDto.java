package com.rm.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder 
@AllArgsConstructor 
@NoArgsConstructor 
@Schema(description = "Standard error response returned by the API.")
public class ErrorResponseDto {

    @Schema(description = "Date and time when the error occurred.")
    private LocalDateTime timestamp;
    @Schema(description = "HTTP status code.")
    private int status;
    @Schema(description = "Short error description.")
    private String error;
    @Schema(description = "Detailed error message.")
    private String message;
    @Schema(description = "API path that produced the error.")
    private String path;
    
    @Schema(description = "Validation errors keyed by field name.")
    private Map<String, String> validationErrors;
}
