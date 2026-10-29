package com.om.exception;

/**
 * OrderNotFoundException
 */
public class OrderNotFoundException extends  RuntimeException{

    public OrderNotFoundException(String message) {
        super(message);
    }
    public OrderNotFoundException(){
        super("Order Not Found");
    }
    


}
