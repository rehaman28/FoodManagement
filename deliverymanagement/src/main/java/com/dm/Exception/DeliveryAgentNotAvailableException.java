package com.dm.exception;

/**
 * DeliveryAgentNotAvailableException
 */
public class DeliveryAgentNotAvailableException extends  RuntimeException{

    public DeliveryAgentNotAvailableException() {
        super("Delivery Agent not Found");
    }

    public DeliveryAgentNotAvailableException(String message) {
        super(message);
    }



}
