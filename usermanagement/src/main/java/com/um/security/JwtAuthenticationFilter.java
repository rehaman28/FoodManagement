package com.um.security;

import java.io.IOException;
import java.util.List;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.um.constants.ApplicationConstants;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserDetailsService userDetailsService) {

        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(ApplicationConstants.AUTHORIZATION_HEADER);
        String requestPath = request.getRequestURI();

        if (authHeader == null || !authHeader.startsWith(ApplicationConstants.BEARER_PREFIX)) {
            log.debug("JWT not supplied: method={}, path={}", request.getMethod(), requestPath);
            filterChain.doFilter(request, response);
            return;
        }

        log.debug("JWT authentication started: method={}, path={}", request.getMethod(), requestPath);

        try {
            String jwt = authHeader.substring(ApplicationConstants.BEARER_PREFIX.length());
            String userEmail = jwtService.extractUsername(jwt);
            String role = jwtService.extractRole(jwt);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            log.debug("JWT claims extracted: subject={}, role={}, path={}", userEmail, role, requestPath);

            if (userEmail != null && authentication == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
                String authority = toAuthority(role);
                boolean tokenValid = jwtService.isTokenValid(jwt, userDetails);
                boolean roleMatches = matchesRole(userDetails, authority);
                log.debug("JWT validation result: subject={}, tokenValid={}, roleMatches={}, authority={}, authorities={}",
                        userEmail, tokenValid, roleMatches, authority, userDetails.getAuthorities());

                if (!tokenValid || !roleMatches) {
                    log.warn("JWT rejected: subject={}, tokenValid={}, roleMatches={}, path={}",
                            userEmail, tokenValid, roleMatches, requestPath);
                    throw new BadCredentialsException("Invalid bearer token");
                }

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                        List.of(new SimpleGrantedAuthority(authority)));
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                log.debug("JWT authentication successful: subject={}, authority={}, path={}",
                        userEmail, authority, requestPath);
            }
        } catch (JwtException exception) {
            SecurityContextHolder.clearContext();
            log.warn("JWT parsing/validation failed: path={}, exception={}",
                    requestPath, exception.getClass().getSimpleName());
            throw new BadCredentialsException("Invalid bearer token", exception);
        } catch (AuthenticationException exception) {
            SecurityContextHolder.clearContext();
            log.warn("JWT authentication failed: path={}, exception={}",
                    requestPath, exception.getClass().getSimpleName());
            throw exception;
        }

        filterChain.doFilter(request, response);
    }

    private String toAuthority(String role) {
        if (role == null || role.isBlank()) {
            return null;
        }

        return role.startsWith(ApplicationConstants.ROLE_PREFIX)
                ? role
                : ApplicationConstants.ROLE_PREFIX + role;
    }

    private boolean matchesRole(UserDetails userDetails, String authority) {
        if (authority == null) {
            return false;
        }

        return userDetails.getAuthorities()
                .stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority));
    }
}