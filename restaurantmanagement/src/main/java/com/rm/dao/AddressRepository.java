package com.rm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rm.model.Address;

public interface AddressRepository extends JpaRepository<Address,Long>{
    
}
