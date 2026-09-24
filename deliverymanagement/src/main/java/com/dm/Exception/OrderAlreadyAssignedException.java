package com.dm.Exception;

public class OrderAlreadyAssignedException extends RuntimeException {

    public OrderAlreadyAssignedException(String message) {
        super(message);
    }

    public OrderAlreadyAssignedException() {
        super("Order already assigned");
    }


}