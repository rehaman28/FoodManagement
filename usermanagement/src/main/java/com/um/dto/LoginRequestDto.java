package com.um.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
@Schema(description = "Credentials required to authenticate a user.")
public class LoginRequestDto {

    @Email
    @NotBlank 
    @Schema(description = "User email address.")
    private  String email;
    @NotBlank 
    @Schema(description = "User password.")
    private  String password;
}
