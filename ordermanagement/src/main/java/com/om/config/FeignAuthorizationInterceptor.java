package com.om.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;

@Configuration
public class FeignAuthorizationInterceptor {

    @Bean
    public RequestInterceptor authorizationInterceptor()
    {
        return 
                requestTemplate ->{
                    if (RequestContextHolder.getRequestAttributes()
                    instanceof ServletRequestAttributes attributes) 
                {
                    String authorization =
                        attributes.getRequest()
                                .getHeader(HttpHeaders.AUTHORIZATION);
                    
                    if (authorization != null && !authorization.isBlank()) {

                    requestTemplate.header(
                            HttpHeaders.AUTHORIZATION,
                            authorization);
                    }
                }
        };

    }

}