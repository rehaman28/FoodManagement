package com.dm.deliverymanagement.model;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long agentId;

    private String agentName;
    private String agentAadhar;
    private String agentPhone;
    private String agentEmail;
    private boolean agentAvailability;
    private List<DeliveryAssignment> deliveryAssignments;
    

    
}
