package com.dm.security;

import java.io.IOException;
import java.util.List;

import io.jsonwebtoken.JwtException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.dm.constants.ApplicationConstants;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader =
                request.getHeader(ApplicationConstants.AUTHORIZATION_HEADER);

        if (authHeader == null ||
                !authHeader.startsWith(ApplicationConstants.BEARER_PREFIX)) {

            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwt = authHeader.substring(ApplicationConstants.BEARER_PREFIX.length());
            String username = jwtService.extractUsername(jwt);
            String role = jwtService.extractRole(jwt);
            if (username == null || username.isBlank() || role == null || role.isBlank()) {
                throw new IllegalArgumentException("JWT is missing its subject or role");
            }

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                String authority = role.startsWith(ApplicationConstants.ROLE_PREFIX)
                        ? role
                        : ApplicationConstants.ROLE_PREFIX + role;
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                List.of(new SimpleGrantedAuthority(authority)));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (JwtException | IllegalArgumentException exception) {

            SecurityContextHolder.clearContext();

            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED);

            response.setContentType("application/json");

            response.getWriter().write("""
                    {
                      "status": 401,
                      "error": "Unauthorized",
                      "message": "Invalid or expired JWT"
                    }
                    """);
                        return;
        }

                filterChain.doFilter(request, response);
    }
}