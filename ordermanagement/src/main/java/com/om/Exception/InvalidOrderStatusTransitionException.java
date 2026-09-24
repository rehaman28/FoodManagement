package com.om.Exception;

public class InvalidOrderStatusTransitionException extends RuntimeException{

    public InvalidOrderStatusTransitionException (String message) {
        super(message);
    }

    public InvalidOrderStatusTransitionException (){

        super("Invalid order status transition");
    }
    
}
