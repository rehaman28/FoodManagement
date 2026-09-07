package com.dm.dto;

import com.dm.model.DeliveryStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    private DeliveryStatus deliveryStatus;


}
