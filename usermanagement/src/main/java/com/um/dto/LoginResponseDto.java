package com.um.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Schema(description = "Response containing the authenticated user token.")
public class LoginResponseDto {

    @Schema(description = "Authentication token.")
    private  String token;
    
}
