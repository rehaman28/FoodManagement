package com.dm.dto;

import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(description = "Response data describing a delivery person and availability.")
public class DeliveryPersonResponeDto {

    @Schema(description = "Identifier of the delivery agent.")
    private long deliveryAgentId;
    @Schema(description = "Full name of the delivery agent.")
    private String deliveryAgentName;
    @Schema(description = "12-digit Aadhaar number of the delivery agent.")
    private String deliveryAgentAadhar;
    @Schema(description = "10-digit phone number of the delivery agent.")
    private String deliveryAgentPhone;
    @Schema(description = "Email address of the delivery agent.")
    private String deliveryAgentEmail;

    @JsonProperty("isAgentAvailable")
    @Schema(description = "Whether the delivery agent is currently available.")
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
