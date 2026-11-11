package com.om.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.om.client.RestaurantClient;
import com.om.dto.response.ItemResponseDto;
import com.om.exception.DownstreamServiceTimeoutException;
import com.om.exception.DownstreamServiceUnavailableException;
import com.om.exception.ItemNotFoundException;
import com.om.exception.RestaurantNotFoundException;
import com.om.helper.DownstreamValidationHelper;

import feign.FeignException;
import feign.RetryableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class RestaurantValidationService {

    private static final Logger log = LoggerFactory.getLogger(RestaurantValidationService.class);

    private final RestaurantClient restaurantClient;
    private final DownstreamValidationHelper downstreamValidationHelper;

    public RestaurantValidationService(
            RestaurantClient restaurantClient,
            DownstreamValidationHelper downstreamValidationHelper) {
        this.restaurantClient = restaurantClient;
        this.downstreamValidationHelper = downstreamValidationHelper;
    }

    @CircuitBreaker(
            name = "restaurantManagement",
            fallbackMethod = "getRestaurantNameFallback"
    )
    public String getRestaurantName(Long restaurantId) {
        try {
            return restaurantClient.getRestaurantName(restaurantId);
        } catch (FeignException.NotFound ex) {
            throw new RestaurantNotFoundException("Restaurant was not found with Id: " + restaurantId);
        } catch (FeignException.ServiceUnavailable ex) {
            throw downstreamValidationHelper.downstreamUnavailable("Restaurant Management", ex);
        } catch (RetryableException ex) {
            if (downstreamValidationHelper.isServiceUnavailable(ex)) {
                throw downstreamValidationHelper.downstreamUnavailable("Restaurant Management", ex);
            }
            throw new DownstreamServiceTimeoutException(
                    "Restaurant Management service did not respond within the configured timeout",
                    ex);
        }
    }

    @CircuitBreaker(
            name = "restaurantManagement",
            fallbackMethod = "getItemFallback"
    )
    public ItemResponseDto getItem(Long restaurantId, Long itemId) {
        try {
            return restaurantClient.getItem(restaurantId, itemId);
        } catch (FeignException.NotFound ex) {
            throw new ItemNotFoundException(
                    "Item id " + itemId + " not found in Restaurant " + restaurantId);
        } catch (FeignException.ServiceUnavailable ex) {
            throw downstreamValidationHelper.downstreamUnavailable("Restaurant Management", ex);
        } catch (RetryableException ex) {
            if (downstreamValidationHelper.isServiceUnavailable(ex)) {
                throw downstreamValidationHelper.downstreamUnavailable("Restaurant Management", ex);
            }
            throw new DownstreamServiceTimeoutException(
                    "Restaurant Management service did not respond within the configured timeout",
                    ex);
        }
    }

    @SuppressWarnings("unused")
    private String getRestaurantNameFallback(Long restaurantId, Throwable ex) {
        if (downstreamValidationHelper.isTimeout(ex)) {
            log.warn(
                    "Restaurant Management timed out while fetching restaurantId={}; returning 504",
                    restaurantId,
                    ex);
            throw new DownstreamServiceTimeoutException(
                    "Restaurant Management service did not respond within the configured timeout",
                    ex);
        }
        log.warn(
                "Restaurant Management is unavailable while fetching restaurantId={}; returning 503",
                restaurantId,
                ex);
        throw new DownstreamServiceUnavailableException(
                "Restaurant Management service is currently unavailable",
                ex);
    }

    @SuppressWarnings("unused")
    private ItemResponseDto getItemFallback(Long restaurantId, Long itemId, Throwable ex) {
        if (downstreamValidationHelper.isTimeout(ex)) {
            log.warn(
                    "Restaurant Management timed out while fetching restaurantId={}, itemId={}; returning 504",
                    restaurantId,
                    itemId,
                    ex);
            throw new DownstreamServiceTimeoutException(
                    "Restaurant Management service did not respond within the configured timeout",
                    ex);
        }
        log.warn(
                "Restaurant Management is unavailable while fetching restaurantId={}, itemId={}; returning 503",
                restaurantId,
                itemId,
                ex);
        throw new DownstreamServiceUnavailableException(
                "Restaurant Management service is currently unavailable",
                ex);
    }

}
