package com.dm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dm.constants.ApplicationConstants;
import com.dm.security.JwtAuthenticationFilter;

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
                .requestMatchers(
                    ApplicationConstants.SWAGGER_UI_PATH,
                    ApplicationConstants.SWAGGER_UI_WILDCARD,
                    ApplicationConstants.API_DOCS_WILDCARD)
                    .permitAll()

                .requestMatchers(ApplicationConstants.DELIVERY_PERSON_WILDCARD)
                        .hasRole("ADMIN")

                .requestMatchers(ApplicationConstants.DELIVERY_ASSIGNMENTS_WILDCARD)
                    .hasRole("ADMIN")

            .anyRequest()
                .authenticated())

            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}