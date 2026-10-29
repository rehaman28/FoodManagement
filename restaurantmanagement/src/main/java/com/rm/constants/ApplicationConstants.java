package com.rm.constants;

public final class ApplicationConstants {

    private ApplicationConstants() {
    }

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String ROLE_PREFIX = "ROLE_";
    public static final String SWAGGER_UI_PATH = "/swagger-ui.html";
    public static final String SWAGGER_UI_WILDCARD = "/swagger-ui/**";
    public static final String API_DOCS_WILDCARD = "/v3/api-docs/**";
    public static final String RESTAURANTS_PATH = "/restaurants";
    public static final String RESTAURANTS_WILDCARD = RESTAURANTS_PATH + "/**";
    public static final String RESTAURANT_ID_PATH = "/{restaurantId}";
    public static final String RESTAURANT_NAME_PATH = "/restaurants/{restaurantId}/name";
    public static final String RESTAURANT_NAME_SUFFIX = "/{restaurantId}/name";
    public static final String RESTAURANT_RATING_PATH = "/{restaurantId}/rating";
    public static final String RESTAURANT_ITEMS_PATH = "/restaurants/{restaurantId}/items";
    public static final String RESTAURANT_ITEMS_WILDCARD = RESTAURANT_ITEMS_PATH + "/**";
    public static final String ITEM_ID_PATH = "/{itemId}";
    public static final String BULK_ITEMS_PATH = "/bulk";
}
