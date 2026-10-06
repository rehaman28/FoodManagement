package com.rm.config;

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

import com.rm.security.JwtAuthenticationFilter;

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

                    //Read Only Resources
                    .requestMatchers(HttpMethod.GET, "/restaurants/**")
                    .hasAnyRole("USER", "ADMIN")

                    //Create Only Resources
                    .requestMatchers(HttpMethod.POST, "/restaurants/**")
                    .hasAnyRole( "ADMIN")

                    //Update Resources
                    .requestMatchers(HttpMethod.PUT, "/restaurants/**")
                    .hasAnyRole( "ADMIN")
                    
                    
                    .requestMatchers(HttpMethod.PATCH,"/restaurants/**")
                    .hasRole("ADMIN")

                    // Delete
                    .requestMatchers(HttpMethod.DELETE,"/restaurants/**")
                    .hasRole("ADMIN")

                    //Any Other Request needs authentications
                    .anyRequest()
                    .authenticated())

            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}