package com.om.exception;

/**
 * RestaurnatNotFoundException
 */
public class RestaurantNotFoundException extends RuntimeException {

    public RestaurantNotFoundException(String message){
        super(message);
    }

    public RestaurantNotFoundException(){
        super("Restaurant Not Found");
    }

}
