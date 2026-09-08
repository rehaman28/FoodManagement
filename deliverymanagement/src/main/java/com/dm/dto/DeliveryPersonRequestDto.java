package com.dm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class DeliveryPersonRequestDto {

    @NotBlank
    @Size(min = 3,max = 50)
    private String deliveryAgentName;

    @NotBlank
    @Size(min = 3,max = 16)
    private String deliveryAgentAadhar;

    @Pattern(
        regexp = "^[6-9]\\d{9}$",
        message = "Phone Number must contain 10 digits"
    )
    private String deliveryAgentPhone; 

    @Pattern(
        regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
        message = "Invalid email format"
    )
    private String deliveryAgentEmail;

    @JsonProperty("isAgentAvailable")
    private boolean agentAvailable;
    
}
