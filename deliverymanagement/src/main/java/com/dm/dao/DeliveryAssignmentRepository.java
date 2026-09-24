package com.dm.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dm.model.DeliveryAssignment;

public interface DeliveryAssignmentRepository extends  JpaRepository<DeliveryAssignment,Long>{
    List<DeliveryAssignment> findByDeliveryPersonDeliveryAgentId(Long deliveryAgentId);
    
    Optional<DeliveryAssignment> findByOrderId(Long orderId);
    
}
