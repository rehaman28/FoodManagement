package com.rm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rm.model.Item;

public interface ItemRepository extends JpaRepository<Item,Long>{
    
}
