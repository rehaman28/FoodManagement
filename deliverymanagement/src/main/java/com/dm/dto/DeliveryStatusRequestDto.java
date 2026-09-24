package com.dm.dto;

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
public class DeliveryStatusRequestDto {

    @JsonProperty("deliveryStatus")
    @NotNull(message = "Delivery status is required")
    private DeliveryStatus deliveryStatus;


}
