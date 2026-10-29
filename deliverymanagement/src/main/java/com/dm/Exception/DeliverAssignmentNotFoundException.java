package com.dm.exception;



/**
 * DeliverAssignmentNotFoundException
 */
public class DeliverAssignmentNotFoundException extends RuntimeException {

    public DeliverAssignmentNotFoundException() {
        super("Delivery Assignment Not Found");
    }

    public DeliverAssignmentNotFoundException(String message) {
        super(message);
    }
    

}
