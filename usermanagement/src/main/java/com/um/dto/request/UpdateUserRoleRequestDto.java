package com.um.dto.request;

import com.um.model.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UpdateUserRoleRequestDto
 */
 
@NoArgsConstructor
@Data 
@AllArgsConstructor 
@Schema(description = "Request to change a user's role. ADMIN authorization is required.")
public class UpdateUserRoleRequestDto {

    @NotNull
    @Schema(
        description = "Target user role",
        example = "ADMIN"
    )
    private Role role;

}
