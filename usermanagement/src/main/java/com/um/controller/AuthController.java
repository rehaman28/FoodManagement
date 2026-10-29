package com.um.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.um.dto.request.LoginRequestDto;
import com.um.dto.response.LoginResponseDto;
import com.um.constants.ApplicationConstants;
import com.um.dto.response.ErrorResponseDto;
import com.um.security.JwtService;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = ApplicationConstants.AUTH_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication Resource", description = "Authentication resource controller")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping(value = ApplicationConstants.LOGIN_ENDPOINT_PATH,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Authenticate a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LoginResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public LoginResponseDto login(
            @Valid @RequestBody LoginRequestDto request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        String role = authentication.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .map(authority -> authority.startsWith(ApplicationConstants.ROLE_PREFIX)
                        ? authority.substring(ApplicationConstants.ROLE_PREFIX.length())
                        : authority)
                .orElseThrow(() -> new IllegalStateException("Authenticated user has no role"));

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put(ApplicationConstants.ROLE_CLAIM, role);
        String token = jwtService.generateToken(extraClaims, authentication.getName());

        return new LoginResponseDto(token);
    }   
}
