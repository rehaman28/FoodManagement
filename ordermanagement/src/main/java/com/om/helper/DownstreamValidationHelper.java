package com.om.helper;

import java.net.SocketTimeoutException;

import org.springframework.stereotype.Component;

import com.om.exception.DownstreamServiceTimeoutException;
import com.om.exception.DownstreamServiceUnavailableException;

import feign.FeignException;
import feign.RetryableException;

@Component
public class DownstreamValidationHelper {

    public DownstreamServiceUnavailableException downstreamUnavailable(String serviceName, Throwable cause) {
        return new DownstreamServiceUnavailableException(serviceName + " service is unavailable", cause);
    }

    public boolean isServiceUnavailable(RetryableException exception) {
        if (exception.status() == 503) {
            return true;
        }
        for (Throwable cause = exception; cause != null; cause = cause.getCause()) {
            if (cause instanceof FeignException.ServiceUnavailable) {
                return true;
            }
        }
        return false;
    }

    public boolean isTimeout(Throwable exception) {
        for (Throwable cause = exception; cause != null; cause = cause.getCause()) {
            if (cause instanceof DownstreamServiceTimeoutException
                    || cause instanceof SocketTimeoutException
                    || cause instanceof RetryableException retryableException
                            && !isServiceUnavailable(retryableException)) {
                return true;
            }
        }
        return false;
    }
}
