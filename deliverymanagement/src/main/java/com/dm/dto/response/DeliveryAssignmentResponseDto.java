package com.dm.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(description = "Response data for an order delivery assignment.")
public class DeliveryAssignmentResponseDto {

    @Schema(description = "Identifier of the delivery assignment.")
    private long deliveryAssignmentId;

    @Schema(description = "Current delivery status.")
    private DeliveryStatus deliveryStatus;
    
    @Schema(description = "Identifier of the order.")
    private Long orderId;
    
    // private long deliveryAgentId;
    // private LocalTime expectedDeliverytime;
    
    @Schema(description = "Date and time when the assignment was created.")
    private LocalDateTime assignmentDate;

    @Schema(description = "Identifier of the delivery agent.")
    private Long deliveryAgentId;

    @Schema(description = "Full name of the delivery agent.")
    private String deliveryAgentName;

}
