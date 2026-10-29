package com.om.constants;

public final class ApplicationConstants {

    private ApplicationConstants() {
    }

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String SWAGGER_UI_PATH = "/swagger-ui.html";
    public static final String SWAGGER_UI_WILDCARD = "/swagger-ui/**";
    public static final String API_DOCS_WILDCARD = "/v3/api-docs/**";
    public static final String ORDERS_PATH = "/orders";
    public static final String ORDERS_WILDCARD = ORDERS_PATH + "/**";
    public static final String PLACE_ORDER_PATH = ORDERS_PATH + "/placeorder";
    public static final String PLACE_ORDER_ENDPOINT_PATH = "/placeorder";
    public static final String ORDER_ID_PATH = "/{orderId}";
    public static final String USER_ORDERS_PATH = "/user/{userId}";
    public static final String RESTAURANT_ORDERS_ID_PATH = "/restaurant/{restaurantId}";
    public static final String ORDER_STATUS_ENDPOINT_PATH = "/status";
    public static final String UPDATE_ORDER_STATUS_PATH = "/{orderId}/status";
    public static final String RESTAURANT_ORDERS_PATH = ORDERS_PATH + "/restaurant/**";
    public static final String ORDER_STATUS_PATH = ORDERS_PATH + "/status";
    public static final String RESTAURANT_SERVICE_NAME = "restaurantmanagement";
    public static final String RESTAURANT_NAME_PATH = "/restaurants/{restaurantId}/name";
    public static final String RESTAURANT_ITEM_PATH = "/restaurants/{restaurantId}/items/{itemId}";
}
