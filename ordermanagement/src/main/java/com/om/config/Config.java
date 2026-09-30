package com.om.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.client.RestTemplate;

@Component
public class Config {

    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            if (RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes attributes) {
                String authorization = attributes.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
                if (authorization != null && !authorization.isBlank()) {
                    request.getHeaders().set(HttpHeaders.AUTHORIZATION, authorization);
                }
            }
            return execution.execute(request, body);
        });
        return restTemplate;
    }
    
}
