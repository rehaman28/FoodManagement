package com.dm.dto;


import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Request data for assigning an order to a delivery agent.")
public class DeliveryAssignmentRequestDto {
    
    @NotNull @Positive 
    @Schema(description = "Identifier of the order.")
    private Long orderId;
    @NotNull @Positive 
    @Schema(description = "Identifier of the delivery agent.")
    private Long deliveryAgentId;

}
