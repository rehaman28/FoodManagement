package com.dm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DeliveryPersoninfoDto
 */
@Data
@NoArgsConstructor
@JsonPropertyOrder({
    "deliveryAgentId",
    "deliveryAgentName",
    "deliveryAgentAadhar",
    "deliveryAgentPhone",
    "deliveryAgentEmail",
    "isAgentAvailable"
})
public class DeliveryPersonResponeDto {

    private long deliveryAgentId;
    private String deliveryAgentName;
    private String deliveryAgentAadhar;
    private String deliveryAgentPhone;
    private String deliveryAgentEmail;

    @JsonProperty("isAgentAvailable")
    private boolean agentAvailable;

    public DeliveryPersonResponeDto(long deliveryAgentId, String deliveryAgentName,
            String deliveryAgentAadhar, String deliveryAgentPhone,
            String deliveryAgentEmail, boolean agentAvailable) {
        this.deliveryAgentId = deliveryAgentId;
        this.deliveryAgentName = deliveryAgentName;
        this.deliveryAgentAadhar = deliveryAgentAadhar;
        this.deliveryAgentPhone = deliveryAgentPhone;
        this.deliveryAgentEmail = deliveryAgentEmail;
        this.agentAvailable = agentAvailable;
    }
}
