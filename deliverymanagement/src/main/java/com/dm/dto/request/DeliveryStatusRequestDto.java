package com.dm.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import com.dm.model.DeliveryStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DeliveryStatusRequestDto
 */
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
@Schema(description = "Request data for changing the delivery status.")
public class DeliveryStatusRequestDto {

    @JsonProperty("deliveryStatus")
    @NotNull(message = "Delivery status is required")
    @Schema(description = "Current delivery status.")
    private DeliveryStatus deliveryStatus;


}
