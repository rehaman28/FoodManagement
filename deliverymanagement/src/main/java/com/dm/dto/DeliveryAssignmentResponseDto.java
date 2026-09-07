package com.dm.dto;

import java.time.LocalDateTime;

import com.dm.model.DeliveryStatus;

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

    private DeliveryStatus deliveryStatus;
    
    private long orderId;
    
    // private long deliveryAgentId;
    // private LocalTime expectedDeliverytime;
    
    private LocalDateTime assignmentDate;

    private Long deliveryAgentId;

    private String deliveryAgentName;

}
