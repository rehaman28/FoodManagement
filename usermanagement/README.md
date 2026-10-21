# User Management Service

## Description

User Management is responsible for user registration, user profile operations, authentication, password encoding, and user roles.

Application:

    usermanagement

Port:

    8002

Base URL:

    http://localhost:8002

## Responsibilities

- Register users
- Retrieve users
- Update users
- Delete users
- Authenticate users
- Generate JWT tokens
- Assign USER / ADMIN roles
- Protect ADMIN-only APIs

## Main API endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| POST | \`/auth/login\` | Authenticate and generate JWT |
| POST | \`/users\` | Register a user |
| GET | \`/users\` | Get all users |
| GET | \`/users/{userId}\` | Get user by ID |
| PATCH | \`/users/{userId}\` | Update user |
| DELETE | \`/users/{userId}\` | Delete user |

## Authentication

### Generate USER JWT

Request:

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

Use:

    Authorization: Bearer <JWT>

### Generate ADMIN JWT

Request:

    POST http://localhost:8002/auth/login

Body:

    {
      "email": "<valid-admin-email>",
      "password": "<valid-admin-password>"
    }

Response:

    {
      "token": "<JWT>"
    }

The ADMIN account must already exist.

## Register user

Request:

    POST http://localhost:8002/users

Body:

    {
      "userName": "Jacob",
      "userPhone": "9876543210",
      "email": "jacob@example.com",
      "password": "Password@123",
      "userAddresses": [
        {
          "doorNumber": "12-3",
          "street": "MG Road",
          "city": "Hyderabad",
          "district": "Hyderabad",
          "country": "India",
          "pincode": "500001"
        }
      ]
    }

Expected:

    201 Created

Example response:

    {
      "userId": 1,
      "userName": "Jacob",
      "userPhone": "9876543210"
    }

Registration is publicly accessible and creates the user as USER.

## Get user

Request:

    GET http://localhost:8002/users/{userId}

Header:

    Authorization: Bearer <JWT>

Expected:

    200 OK

Response:

    {
      "userId": 1,
      "userName": "Jacob",
      "userPhone": "9876543210"
    }

## Get all users

Request:

    GET http://localhost:8002/users

Header:

    Authorization: Bearer <ADMIN_JWT>

Expected:

    200 OK

Response:

    [
      {
        "userId": 1,
        "userName": "Jacob",
        "userPhone": "9876543210"
      }
    ]

## Update user

Request:

    PATCH http://localhost:8002/users/{userId}

Body:

    {
      "userName": "Jacob Updated"
    }

Expected:

    200 OK

## Delete user

Request:

    DELETE http://localhost:8002/users/{userId}

Header:

    Authorization: Bearer <ADMIN_JWT>

Expected:

    204 No Content

## Basic testing flow

    Start User Management :8002
        |
        v
    POST /auth/login
        |
        v
    Receive { "token": "<JWT>" }
        |
        +--> USER JWT -> authenticated USER APIs
        |
        +--> ADMIN JWT -> ADMIN APIs
        |
        v
    Run CRUD requests in Postman

## Postman collection

Use:

    /postman/FoodManagement-Login.postman_collection.json

and:

    /postman/FoodManagement-UserManagement.postman_collection.json

Environment:

    FoodManagement-USER
    FoodManagement-ADMIN

## Role model

- USER: normal authenticated user
- ADMIN: administrative user with access to user-management administration endpoints

## Implementation scope

Current implementation includes:

- Spring Boot REST APIs
- JPA / MySQL
- Bean validation
- Password encoding
- JWT authentication
- Role-based authorization
- Eureka client registration

Future platform phases add service-to-service communication, messaging, resilience, testing, containerization, CI/CD, and observability.
