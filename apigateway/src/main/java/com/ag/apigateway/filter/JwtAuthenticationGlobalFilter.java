package com.ag.apigateway.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ag.apigateway.config.JwtService;

@Component
public class JwtAuthenticationGlobalFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String LOGIN_PATH = "/auth/login";
    private static final String USERS_PATH = "/users";

    private final JwtService jwtService;

    public JwtAuthenticationGlobalFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        if (isPublicEndpoint(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith(BEARER_PREFIX)) {
            unauthorized(response, "Missing or invalid Authorization header");
            return;
        }

        String token = authorization.substring(BEARER_PREFIX.length()).trim();
        if (token.isEmpty() || !jwtService.isValid(token)) {
            unauthorized(response, "Invalid or expired JWT");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPublicEndpoint(HttpServletRequest request) {
        String method = request.getMethod();
        String path = request.getRequestURI();

        return ("POST".equals(method) && LOGIN_PATH.equals(path))
                || ("POST".equals(method) && USERS_PATH.equals(path))
                || "OPTIONS".equals(method);
    }

    private void unauthorized(HttpServletResponse response, String message)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write("""
                {
                  "status": 401,
                  "error": "Unauthorized",
                  "message": "%s"
                }
                """.formatted(message));
    }
}
