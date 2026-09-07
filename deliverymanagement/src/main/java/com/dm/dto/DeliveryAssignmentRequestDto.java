package com.dm.dto;

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
    
    private Long orderId;
    private Long deliveryAgentId;

}
