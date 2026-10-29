package com.dm.constants;

public final class ApplicationConstants {

    private ApplicationConstants() {
    }

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String SWAGGER_UI_PATH = "/swagger-ui.html";
    public static final String SWAGGER_UI_WILDCARD = "/swagger-ui/**";
    public static final String API_DOCS_WILDCARD = "/v3/api-docs/**";
    public static final String DELIVERY_PERSON_PATH = "/delivery-person";
    public static final String DELIVERY_PERSON_WILDCARD = DELIVERY_PERSON_PATH + "/**";
    public static final String ADD_PERSON_PATH = "/addperson";
    public static final String DELIVERY_PERSON_ID_PATH = "/{deliveryAgentId}";
    public static final String UPDATE_PERSON_PATH = "/update/{deliveryAgentId}";
    public static final String DELIVERY_ASSIGNMENTS_PATH = "/delivery-assignments";
    public static final String DELIVERY_ASSIGNMENTS_WILDCARD = DELIVERY_ASSIGNMENTS_PATH + "/**";
    public static final String DELIVERY_ASSIGNMENT_ID_PATH = "/{deliveryAssignmentId}";
    public static final String ALL_ASSIGNMENTS_PATH = "/all";
    public static final String PERSON_ASSIGNMENTS_PATH = "/delivery-person/{deliveryAgentId}/assignments";
    public static final String ASSIGNMENT_STATUS_PATH = "/{deliveryAssignmentId}/status";
}
