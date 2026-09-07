package com.dm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dm.model.DeliveryPerson;

public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson, Long> {
    
}
