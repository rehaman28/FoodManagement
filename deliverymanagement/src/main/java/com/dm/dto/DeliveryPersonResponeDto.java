package com.dm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DeliveryPersoninfoDto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPersonResponeDto {

    private long deliveryAgentId;
    private String deliveryAgentName;
    private String delieryAgentAadhar;
    private String deliveryAgentPhone;
    private String deliveryAgentEmail;
    private boolean isAgentAvailable;

    public DeliveryPersonResponeDto(long deliveryAgentId, String deliveryAgentName, String deliveryAgentPhone,
            boolean isAgentAvailable) {
        this.deliveryAgentId = deliveryAgentId;
        this.deliveryAgentName = deliveryAgentName;
        this.deliveryAgentPhone = deliveryAgentPhone;
        this.isAgentAvailable = isAgentAvailable;
    }

    public DeliveryPersonResponeDto(long deliveryAgentId, String deliveryAgentName, boolean isAgentAvailable) {
        this.deliveryAgentId = deliveryAgentId;
        this.deliveryAgentName = deliveryAgentName;
        this.isAgentAvailable = isAgentAvailable;
    }
    

}
