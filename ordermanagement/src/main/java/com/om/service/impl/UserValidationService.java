package com.om.service.impl;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.om.client.UserClient;
import com.om.dto.response.UserResponseDto;
import com.om.exception.DownstreamServiceTimeoutException;
import com.om.exception.UserManagementUnavailableException;
import com.om.exception.UserNotFoundException;
import com.om.helper.DownstreamValidationHelper;

import feign.FeignException;
import feign.RetryableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

//Helper Methods to validate user Synchronously
@Service
public class UserValidationService {

    private static final Logger log =
            LoggerFactory.getLogger(UserValidationService.class);

    private final UserClient userClient;
    private final DownstreamValidationHelper downstreamValidationHelper;

    public UserValidationService(
            UserClient userClient,
            DownstreamValidationHelper downstreamValidationHelper) {
        this.userClient = userClient;
        this.downstreamValidationHelper = downstreamValidationHelper;
    }

    @CircuitBreaker(
            name = "userManagement",
            fallbackMethod = "getUserFallback"
    )
    public UserResponseDto validateUser(Long userId) {
        try {
            return userClient.getUserById(userId);
        } catch (FeignException.NotFound e) {

            throw new UserNotFoundException(
                    "User was not found with Id: "
                            + userId);
        } catch (FeignException.ServiceUnavailable ex) {
            throw downstreamValidationHelper.downstreamUnavailable("User Management", ex);
        } catch (RetryableException ex) {
            if (downstreamValidationHelper.isServiceUnavailable(ex)) {
                throw downstreamValidationHelper.downstreamUnavailable("User Management", ex);
            }
            throw new DownstreamServiceTimeoutException(
                    "User Management service did not respond within the configured timeout",
                    ex);
        }
    }

    @SuppressWarnings("unused")
    private UserResponseDto getUserFallback(
            Long userId,
            Throwable ex) {
        if (downstreamValidationHelper.isTimeout(ex)) {
            log.warn(
                    "User Management timed out while fetching userId={}; returning 504",
                    userId,
                    ex);
            throw new DownstreamServiceTimeoutException(
                    "User Management service did not respond within the configured timeout",
                    ex);
        }
        log.warn(
                "User Management is unavailable while fetching userId={}; returning 503",
                userId,
                ex);
        throw new UserManagementUnavailableException(
                "User Management service is currently unavailable",
                ex);
    }

}
