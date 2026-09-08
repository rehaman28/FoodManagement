package com.dm.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DeliveryAssignmentRequestDto
 */
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class DeliveryAssignmentRequestDto {
    
    @NotNull @Positive 
    private Long orderId;
    @NotNull @Positive 
    private Long deliveryAgentId;

}
