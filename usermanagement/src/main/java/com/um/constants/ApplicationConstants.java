package com.um.constants;

public final class ApplicationConstants {

    private ApplicationConstants() {
    }

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String SWAGGER_UI_PATH = "/swagger-ui.html";
    public static final String SWAGGER_UI_WILDCARD = "/swagger-ui/**";
    public static final String API_DOCS_WILDCARD = "/v3/api-docs/**";
    public static final String AUTH_PATH = "/auth";
    public static final String LOGIN_PATH = AUTH_PATH + "/login";
    public static final String LOGIN_ENDPOINT_PATH = "/login";
    public static final String USERS_PATH = "/users";
    public static final String USERS_WILDCARD = USERS_PATH + "/**";
    public static final String USER_ID_PATH = "/{userId}";
    public static final String ROLE_CLAIM = "role";
}
