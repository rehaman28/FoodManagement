package com.dm.deliverymanagement.model;

import java.time.LocalTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "delivery_assignments")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DeliveryAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long deliveryAssignmentId;
    private String deliveryStatus;
    private long orderId;
    // private long deliveryAgentId;
    // private LocalTime expectedDeliverytime;
    private LocalTime assignmentDate;
 
    @ManyToOne
    @JoinColumn(name="delivery_person_id",nullable = false)
    private DeliveryPerson deliveryPerson;
}
