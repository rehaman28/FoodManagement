package com.dm.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
@Schema(description = "Request data for creating or updating a delivery person.")
public class DeliveryPersonRequestDto {

    @NotBlank
    @Size(min = 3,max = 50)
    @Schema(description = "Full name of the delivery agent.")
    private String deliveryAgentName;

    @Pattern(
    regexp = "^[2-9][0-9]{11}$",
    message = "Aadhar must contain 12 digits"
)
    @Schema(description = "12-digit Aadhaar number of the delivery agent.")
    private String deliveryAgentAadhar;

    @Pattern(
        regexp = "^[6-9]\\d{9}$",
        message = "Phone Number must contain 10 digits"
    )
    @Schema(description = "10-digit phone number of the delivery agent.")
    private String deliveryAgentPhone; 

    @Pattern(
        regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
        message = "Invalid email format"
    )
    @Schema(description = "Email address of the delivery agent.")
    private String deliveryAgentEmail;

    
}
