package com.dm.dto;

import java.time.LocalTime;

import com.dm.model.DeliveryPerson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DeliveryAssignmentResponseDto
 */
@AllArgsConstructor 
@Data @NoArgsConstructor 
public class DeliveryAssignmentResponseDto {

    private long deliveryAssignmentId;

    private String deliveryStatus;
    
    private long orderId;
    
    // private long deliveryAgentId;
    // private LocalTime expectedDeliverytime;
    
    private LocalTime assignmentDate;

    private DeliveryPerson deliveryPerson;

}
