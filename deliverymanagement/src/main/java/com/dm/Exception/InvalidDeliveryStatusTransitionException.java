package com.dm.Exception;

/**
 * InvalidDeliveryStatusTransitionException
 */
public class InvalidDeliveryStatusTransitionException  extends  RuntimeException{

    public InvalidDeliveryStatusTransitionException() {
        super("Invalid delivery status transition");
    }

    public InvalidDeliveryStatusTransitionException(String message) {
        super(message);
    }

}
