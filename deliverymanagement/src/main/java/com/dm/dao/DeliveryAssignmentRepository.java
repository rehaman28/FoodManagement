package com.dm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dm.model.DeliveryAssignment;

public interface DeliveryAssignmentRepository extends  JpaRepository<DeliveryAssignment,Long>{
    List<DeliveryAssignment> findByDeliveryPersonDeliveryAgentId(Long deliveryAgentId);
    
}
