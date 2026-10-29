package com.um.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor 
@Schema(description = "Response data describing a user.")
public class UserResponseDto {
    
    @Schema(description = "Identifier of the user placing the order.")
    private long userId;
    @Schema(description = "User name.")
    private String userName;
    @Schema(description = "User phone number.")
    private String userPhone;
    
}
