package com.rm.controller;

import org.springframework.web.bind.annotation.RestController;

import com.rm.model.Restaurant;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
public class RestaurantController {

    @PostMapping("/addrestaurant")    
    public Restaurant addRestaurant(){
        return null;
    }
    
}
