package com.dm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class DeliveryPersonRequestDto {

    private String deliveryAgentName;
    private String delieryAgentAadhar;
    private String deliveryAgentPhone; 
    private String deliveryAgentEmail;
    private boolean isAgentAvailable;
    
}
