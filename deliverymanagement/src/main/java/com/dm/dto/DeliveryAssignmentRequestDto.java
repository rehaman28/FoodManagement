package com.dm.dto;

import com.dm.model.DeliveryPerson;

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
    
    private long deliveryAssignmentId;

    private String deliveryStatus;
    
    private long orderId;

    private DeliveryPerson deliveryPerson;

}
