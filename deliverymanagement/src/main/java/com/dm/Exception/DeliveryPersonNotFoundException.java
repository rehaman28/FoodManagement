package com.dm.Exception;

/**
 * DeliveryPersonNotFoundException
 */
public class DeliveryPersonNotFoundException extends RuntimeException {

    public DeliveryPersonNotFoundException() {
        super("Delivery Agent Not Found");
    }

    public DeliveryPersonNotFoundException(String message) {
        super(message);
    }
    

}
