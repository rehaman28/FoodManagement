package com.om.exception;

public class DownstreamServiceTimeoutException extends  RuntimeException { 
    
    public DownstreamServiceTimeoutException(String message) {
        super(message);
    }

    public DownstreamServiceTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
