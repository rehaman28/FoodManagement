# Restaurant Management Service

## Description

Restaurant Management owns restaurants, restaurant addresses, food items, ratings, and restaurant/item retrieval used by other services.

Application / service ID:

    restaurantmanagement

Repository directory:

    restaurantmanagement

Port:

    8001

Base URL:

    http://localhost:8001

Swagger UI:

    http://localhost:8001/swagger-ui/index.html

OpenAPI specification:

    http://localhost:8001/v3/api-docs

## Responsibilities

- Create restaurants
- Retrieve restaurants
- Update restaurants
- Delete restaurants
- Update restaurant ratings
- Retrieve restaurant names
- Create restaurant items
- Bulk create items
- Retrieve items
- Update items
- Delete items

## Restaurant APIs

| Method | Endpoint | Purpose |
|---|---|---|
| POST | \`/restaurants\` | Create restaurant |
| GET | \`/restaurants/{restaurantId}\` | Get restaurant |
| GET | \`/restaurants/{restaurantId}/name\` | Get restaurant name |
| GET | \`/restaurants\` | Get all restaurants |
| PATCH | \`/restaurants/{restaurantId}\` | Update restaurant |
| DELETE | \`/restaurants/{restaurantId}\` | Delete restaurant |
| PUT | \`/restaurants/{restaurantId}/rating\` | Update rating |

## Item APIs

| Method | Endpoint | Purpose |
|---|---|---|
| GET | \`/restaurants/{restaurantId}/items\` | Get restaurant items |
| GET | \`/restaurants/{restaurantId}/items/{itemId}\` | Get one item |
| POST | \`/restaurants/{restaurantId}/items\` | Add item |
| POST | \`/restaurants/{restaurantId}/items/bulk\` | Add multiple items |
| PATCH | \`/restaurants/{restaurantId}/items/{itemId}\` | Update item |
| DELETE | \`/restaurants/{restaurantId}/items/{itemId}\` | Delete item |

## Authentication

Restaurant Management is secured by JWT.

Generate a USER JWT through User Management:

    POST http://localhost:8002/auth/login

Body:

    {
      "email": "<valid-user-email>",
      "password": "<valid-user-password>"
    }

Response:

    {
      "token": "<JWT>"
    }

Generate an ADMIN JWT the same way with an existing ADMIN account.

Use:

    Authorization: Bearer <JWT>

Typical access model:

- USER: restaurant read operations
- ADMIN: restaurant/item create, update, and delete operations

## Create restaurant

Request:

    POST http://localhost:8001/restaurants

Header:

    Authorization: Bearer <ADMIN_JWT>

Body:

    {
      "restaurantName": "Arugu",
      "phoneNumber": "9123456789",
      "rating": 4.2,
      "addressRequestDto": {
        "landmark": "InOrbit Mall",
        "city": "Hyderabad",
        "state": "Telangana",
        "pincode": "500081"
      },
      "itemRequestDto": [
        {
          "itemName": "Paneer Bowl",
          "itemPrice": 180,
          "itemCategory": "Main Course",
          "itemType": "Veg",
          "itemRating": 4
        }
      ]
    }

Expected:

    201 Created

Example response:

    {
      "Id": 1,
      "Name": "Arugu",
      "Rating": 4.2
    }

## Get restaurant

Request:

    GET http://localhost:8001/restaurants/{restaurantId}

Header:

    Authorization: Bearer <USER_JWT>

Expected:

    200 OK

The response contains restaurant information and associated data exposed by the service.

## Get restaurant name

Request:

    GET http://localhost:8001/restaurants/{restaurantId}/name

Header:

    Authorization: Bearer <USER_JWT>

Example response:

    "Arugu"

This endpoint is also used by Order Management for restaurant lookups.

## Get all restaurants

Request:

    GET http://localhost:8001/restaurants

Header:

    Authorization: Bearer <USER_JWT>

Example response:

    [
      {
        "Id": 1,
        "Name": "Arugu",
        "Rating": 4.2
      }
    ]

## Add item

Request:

    POST http://localhost:8001/restaurants/{restaurantId}/items

Header:

    Authorization: Bearer <ADMIN_JWT>

Body:

    {
      "itemName": "Chicken Bowl",
      "itemPrice": 270,
      "itemCategory": "Main Course",
      "itemType": "Non-Veg",
      "itemRating": 4.5
    }

Expected:

    201 Created

Example response:

    {
      "itemId": 2,
      "itemName": "Chicken Bowl",
      "itemPrice": 270.0,
      "itemCategory": "Main Course",
      "itemType": "Non-Veg",
      "itemRating": 4.5
    }

## Basic testing flow

    Generate USER or ADMIN JWT from User Management
        |
        v
    Set Authorization: Bearer <JWT>
        |
        v
    Call Restaurant APIs
        |
        v
    Read HTTP status + JSON response

## Postman collection

Use:

    /postman/FoodManagement-RestaurantManagement.postman_collection.json

Environment:

    FoodManagement-USER
    FoodManagement-ADMIN

## Inter-service responsibility

Order Management currently depends on these Restaurant APIs:

    GET /restaurants/{restaurantId}/name

    GET /restaurants/{restaurantId}/items/{itemId}

The next architecture phase replaces the current direct RestTemplate URL with OpenFeign + Eureka service discovery.
