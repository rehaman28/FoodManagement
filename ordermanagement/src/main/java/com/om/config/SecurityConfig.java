package com.om.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.om.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter) {

        this.jwtAuthenticationFilter =
                jwtAuthenticationFilter;
    }

    @Bean
    AuthenticationEntryPoint authenticationEntryPoint() {

        return new HttpStatusEntryPoint(
                HttpStatus.UNAUTHORIZED);
    }

    @Bean
    SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationEntryPoint authenticationEntryPoint)
            throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS))

            .exceptionHandling(exception ->
                exception.authenticationEntryPoint(
                    authenticationEntryPoint))

            .authorizeHttpRequests(request -> request

            .requestMatchers(HttpMethod.POST, "/orders/placeorder")
                .hasAnyRole("USER", "ADMIN")

            .requestMatchers(HttpMethod.GET, "/orders")
                .hasRole("ADMIN")

            .requestMatchers(HttpMethod.GET, "/orders/restaurant/**")
                .hasRole("ADMIN")

            .requestMatchers(HttpMethod.GET, "/orders/status")
                .hasRole("ADMIN")

            .requestMatchers(HttpMethod.PATCH, "/orders/**")
                .hasRole("ADMIN")

            .requestMatchers(HttpMethod.GET, "/orders/**")
                .hasAnyRole("USER", "ADMIN")

            .anyRequest()
            .authenticated())

            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}