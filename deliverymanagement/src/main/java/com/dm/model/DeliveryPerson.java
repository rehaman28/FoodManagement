package com.dm.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "delivery_persons")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DeliveryPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long deliveryAgentId;

    private String deliveryAgentName;
    private String delieryAgentAadhar;
    private String deliveryAgentPhone; 
    private String deliveryAgentEmail;
    private boolean isAgentAvailable;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "delivery_Person")
    private List<DeliveryAssignment> deliveryAssignments;
    
}
