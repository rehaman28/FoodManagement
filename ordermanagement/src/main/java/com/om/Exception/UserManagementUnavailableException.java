package com.om.exception;

/**
 * UserManagementUnavailableException
 */
public class UserManagementUnavailableException extends RuntimeException{

    public UserManagementUnavailableException(String message,Throwable exThrowable) {
        super(message,exThrowable);
    }

    public UserManagementUnavailableException() {
        super("UserManagement is not available");
    }

}
