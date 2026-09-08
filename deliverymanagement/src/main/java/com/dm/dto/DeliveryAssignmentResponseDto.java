package com.dm.dto;

import java.time.LocalDateTime;

import com.dm.model.DeliveryStatus;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DeliveryAssignmentResponseDto
 */
@AllArgsConstructor 
@Data @NoArgsConstructor 
@JsonPropertyOrder(
    {
    "orderId",
    "deliveryAgentId",
    "deliveryAgentName",
    "deliveryAssignmentId",
    "assignmentDate",
    "deliveryStatus",
}
)
public class DeliveryAssignmentResponseDto {

    private long deliveryAssignmentId;

    private DeliveryStatus deliveryStatus;
    
    private Long orderId;
    
    // private long deliveryAgentId;
    // private LocalTime expectedDeliverytime;
    
    private LocalDateTime assignmentDate;

    private Long deliveryAgentId;

    private String deliveryAgentName;

}
