package com.om.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.om.constants.ApplicationConstants;
import com.om.dto.UserResponseDto;

@FeignClient(name = ApplicationConstants.USER_SERVICE_NAME)
public interface UserClient {

    @GetMapping(ApplicationConstants.USER_PATH)
    UserResponseDto  getUserById(
            @PathVariable("userId") Long userId);

}
