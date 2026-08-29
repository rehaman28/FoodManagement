package com.rm.Exception;

/**
 * RestaurnatNotFoundException
 */
public class RestaurnatNotFoundException extends RuntimeException {

    public RestaurnatNotFoundException(String message){
        super(message);
    }

    public RestaurnatNotFoundException(){
        super("Restaurant Not Found");
    }

}
