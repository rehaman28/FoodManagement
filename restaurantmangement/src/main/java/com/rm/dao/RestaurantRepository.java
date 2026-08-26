package com.rm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rm.model.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long>{
    
}
