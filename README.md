# FoodManagement

FoodManagement is a Spring Boot microservices project containing:

- User Management
- Restaurant Management
- Order Management
- Delivery Management
- Eureka Service Discovery
- JWT authentication
- Role-Based Authorization (USER / ADMIN)
- Postman API and security regression collections

## Current roadmap

| Phase | Status |
|---|---|
| 1. User Management | ✅ Complete |
| 2. Restaurant Management | ✅ Complete |
| 3. Order Management | ✅ Core complete |
| 4. Delivery Management | ✅ Core complete |
| 5. API Test Baseline / Postman | ✅ Complete |
| 6. JWT Authentication | ✅ Complete |
| 7. RBAC | ✅ Complete |
| 8. Security Postman Regression | ✅ Complete |
| 9. Eureka Service Discovery | ✅ Complete and validated |
| 10. OpenFeign + Eureka integration | 🔵 Current target |

## Repository status

The current main branch contains the Phase 9 Eureka implementation.

Current services:

| Service | Spring application name | Port |
|---|---|---:|
| Eureka | eureka | 8761 |
| User Management | usermanagement | 8002 |
| Restaurant Management | restaurantmangement | 8001 |
| Order Management | ordermanagement | 8003 |
| Delivery Management | deliverymanagement | 8004 |

Important: the repository directory is restaurantmanagement, but the existing Eureka/service ID is restaurantmangement. Do not rename the service ID during Phase 10 unless the entire discovery configuration is intentionally changed.

## Prerequisites

- Java 21
- Gradle / Gradle Wrapper
- MySQL running locally
- Postman
- Eureka and the four business services available on the ports listed above

Do not put production passwords, JWT secrets, or production credentials in this README.

## Startup order

Start the applications in this order:

1. MySQL
2. Eureka Server
3. User Management
4. Restaurant Management
5. Order Management
6. Delivery Management

Eureka dashboard:

    http://localhost:8761

Expected registered applications:

    USERMANAGEMENT
    RESTAURANTMANGEMENT
    ORDERMANAGEMENT
    DELIVERYMANAGEMENT

## Postman assets

The canonical regression assets for this project are under:

    /postman

### Collections

    FoodManagement-Login.postman_collection.json
    FoodManagement-UserManagement.postman_collection.json
    FoodManagement-RestaurantManagement.postman_collection.json
    FoodManagement-OrderManagement.postman_collection.json
    FoodManagement-DeliveryManagement.postman_collection.json
    FoodManagement-Overall.postman_collection.json

### Environments

    FoodManagement-USER.postman_environment.json
    FoodManagement-ADMIN.postman_environment.json
    FoodManagement-environment.postman_environment.json

Use USER and ADMIN environments for JWT/RBAC validation.

## Postman setup

### Import

Import all six collections from /postman into Postman.

Import these environments:

    FoodManagement-USER
    FoodManagement-ADMIN

The base environment can be used for non-role-specific local API testing, but USER and ADMIN are the environments required for JWT/RBAC regression.

### USER environment

Expected variables include:

    role = USER
    loginEmail
    loginPassword
    TOKEN

The login email and password must belong to a valid USER in the local User Management database.

### ADMIN environment

Expected variables include:

    role = ADMIN
    loginEmail
    loginPassword
    TOKEN

The ADMIN environment intentionally does not contain a repository-supplied ADMIN password. Populate it with a valid existing ADMIN account.

## Login validation

Select the environment first.

Run:

    FoodManagement - Login
        -> Login - Current Environment

Expected response:

    200 OK

The login test verifies:

- HTTP status is 200
- response contains a token
- token has three JWT sections
- JWT subject matches loginEmail
- JWT role matches the active environment
- TOKEN is stored in the active Postman environment

After a successful login, verify that Postman's active environment contains a non-empty TOKEN.

Do not interpret a secured endpoint returning 401 as an RBAC failure until login and TOKEN population have been verified.

## Postman execution strategy

There are two validation modes.

### 1. Functional regression

Use:

    FoodManagement-ADMIN

Then run:

    FoodManagement - Overall API + Security Regression

The ADMIN environment is recommended for the complete functional baseline because several management endpoints are intentionally restricted to ADMIN.

### 2. USER security regression

Switch to:

    FoodManagement-USER

Run the service collections and focus on the requests prefixed with [RBAC].

Some existing functional requests are expected to return 403 for USER because the underlying endpoint is ADMIN-only. Those are not failures when the authorization policy is being tested.

## Current RBAC validation matrix

| Service | Request | USER | ADMIN |
|---|---|---:|---:|
| User Management | Get All Users | 403 | 200 |
| User Management | Delete User using non-existent ID | 403 | 404 |
| Restaurant Management | Add Restaurant using invalid body | 403 | 400 |
| Restaurant Management | Delete Restaurant using non-existent ID | 403 | 404 |
| Order Management | Get All Orders | 403 | 200 |
| Order Management | Get Orders By Status | 403 | 200 |
| Delivery Management | Get All Delivery Agents | 403 | 200 |
| Delivery Management | Get All Assignments | 403 | 200 |

Interpret the status codes as follows:

    401 = authentication failed, missing, invalid, or expired JWT
    403 = valid JWT but role is not allowed
    2xx = authorization succeeded and request was processed
    4xx after ADMIN authorization can be business validation, not RBAC failure

## Unauthenticated validation

Existing service collections contain explicit no-token checks.

Examples:

    User Management:
        Get All Users - No Token

    Restaurant Management:
        Get All Restaurants - No Token

    Order Management:
        Get All Orders - No Token

    Delivery Management:
        Get All Delivery Agents - No Token

Expected result:

    401 Unauthorized

## Individual collection validation

### User Management

Collection:

    FoodManagement-UserManagement.postman_collection.json

Validate:

- registration
- validation failures
- authenticated user retrieval/update
- ADMIN-only get-all users
- ADMIN-only delete
- not-found cases
- no-token case
- RBAC cases

### Restaurant Management

Collection:

    FoodManagement-RestaurantManagement.postman_collection.json

Validate:

- restaurant creation
- restaurant retrieval
- restaurant update/delete
- restaurant name
- item retrieval
- item creation
- bulk item creation
- rating
- validation and not-found scenarios
- RBAC cases

### Order Management

Collection:

    FoodManagement-OrderManagement.postman_collection.json

Validate:

- place order
- restaurant lookup
- item lookup and price calculation
- get order
- get all orders
- get orders by user
- get orders by restaurant
- get orders by status
- status transitions
- invalid/not-found scenarios
- RBAC cases

### Delivery Management

Collection:

    FoodManagement-DeliveryManagement.postman_collection.json

Validate:

- delivery-person lifecycle
- assignment creation
- assignment retrieval
- assignment listing
- delivery status transitions
- conflict/validation/not-found scenarios
- RBAC cases

## Recommended full Postman flow

    Start MySQL
        |
        v
    Start Eureka :8761
        |
        v
    Start User Management :8002
        |
        v
    Start Restaurant Management :8001
        |
        v
    Start Order Management :8003
        |
        v
    Start Delivery Management :8004
        |
        v
    Verify Eureka dashboard
        |
        v
    Select FoodManagement-USER
        |
        v
    Run Login - Current Environment
        |
        v
    Verify 200 + TOKEN
        |
        v
    Run USER RBAC checks
        |
        v
    Switch to FoodManagement-ADMIN
        |
        v
    Run Login - Current Environment
        |
        v
    Verify 200 + TOKEN
        |
        v
    Run ADMIN functional baseline
        |
        v
    Run Overall collection

## Phase 10 validation target

Phase 9 proves that services register with Eureka.

Phase 10 must prove that Order Management actually uses Eureka to find Restaurant Management.

### Current Order Management baseline

At the current repository state, Order Management still uses RestTemplate and hard-coded Restaurant URLs.

The current calls are:

    http://localhost:8001/restaurants/{restaurantId}/name

    http://localhost:8001/restaurants/{restaurantId}/items/{itemId}

The current RestTemplate configuration also propagates the incoming Authorization header to Restaurant Management.

Therefore, the Phase 10 Postman flow should be executed only as an acceptance test after the OpenFeign migration is implemented.

## Phase 10 Postman acceptance scenario

### Step 1 — Normal order flow

Start:

    Eureka :8761
    Restaurant Management :8001
    Order Management :8003

Select:

    FoodManagement-USER

Run:

    FoodManagement - Login
        -> Login - Current Environment

Verify:

    200 OK
    TOKEN is populated

Then run:

    FoodManagement-OrderManagement
        -> Place Order - Happy

Expected:

    201 Created

The Order service should reach Restaurant Management successfully through the Feign/Eureka path after the migration.

### Step 2 — JWT propagation

Because Restaurant Management is secured, Order Management must pass the incoming Authorization header to the Feign request.

Expected flow:

    Postman
       |
       | Bearer JWT
       v
    Order Management
       |
       | Feign + Bearer JWT
       v
    Restaurant Management

Expected result:

    no unexpected 401
    no unexpected 403
    business response is returned normally

### Step 3 — Restaurant not found

Use an invalid restaurant ID such as:

    999999999

Expected Order API result:

    404 Restaurant Not Found

The application should translate the downstream Feign 404 into the existing business exception/response rather than exposing a raw Feign exception.

### Step 4 — Item not found

Use an invalid item ID.

Expected result:

    404 Item Not Found

The external Postman behavior should remain compatible with the existing API baseline.

## Proving Eureka discovery is really being used

A normal successful Postman request is not enough to prove service discovery.

After the OpenFeign implementation is completed:

1. Run Restaurant Management with:
   
       spring.application.name=restaurantmangement

       server.port=8001

2. Start a second Restaurant Management instance using the same application name on another port, for example 8005.

3. Verify Eureka shows two Restaurant instances.

4. Keep the 8005 instance running and stop the 8001 instance.

5. Run the Order Management place-order request again.

6. The order flow should still work without changing the Postman restaurant URL.

This is the strongest validation that Order Management is resolving restaurantmangement through Eureka/LoadBalancer instead of using localhost:8001 directly.

## Expected Phase 10 regression behavior

| Scenario | Expected |
|---|---|
| Valid USER login | 200 |
| Valid ADMIN login | 200 |
| Secured endpoint without JWT | 401 |
| USER on ADMIN-only endpoint | 403 |
| ADMIN on ADMIN endpoint | 2xx or expected business 4xx |
| USER places order | 201 |
| Restaurant not found during order | 404 |
| Item not found during order | 404 |
| Existing Restaurant APIs | unchanged |
| Existing Delivery APIs | unchanged |
| Existing RBAC tests | unchanged |

## Troubleshooting

### All secured Postman requests return 401

Check:

1. Correct environment is active.
2. Login request returned 200.
3. TOKEN is populated.
4. The request sends Bearer TOKEN.
5. JWT has not expired.
6. All services use the same JWT secret.

### USER RBAC request returns 403

This is usually correct when the endpoint is ADMIN-only.

Example:

    USER + GET /users = 403

### USER RBAC request returns 401

This indicates an authentication/token problem, not a role problem.

Check login and TOKEN first.

### Order returns 401 after Feign migration

Check Feign Authorization propagation from Order Management to Restaurant Management.

### Feign cannot resolve restaurantmangement

Check:

- Eureka is running.
- Restaurant Management is visible in Eureka.
- Feign service name exactly matches restaurantmangement.
- Eureka client is enabled in Order Management.
- Spring Cloud LoadBalancer dependency is present.

## Security note

The current repository contains local-development configuration and sample Postman credentials. Before production use, externalize:

- database passwords
- JWT secrets
- ADMIN credentials
- other environment-specific configuration

Use environment variables, deployment secrets, or a secrets manager.

## Phase 10 completion checklist

- [ ] OpenFeign dependency added
- [ ] LoadBalancer dependency added
- [ ] Feign clients enabled
- [ ] RestaurantClient created
- [ ] Order → Restaurant calls migrated from RestTemplate
- [ ] JWT Authorization propagated through Feign
- [ ] Feign 404 mapped to existing business exceptions
- [ ] Obsolete RestTemplate configuration removed
- [ ] Hard-coded localhost:8001 removed from Order Management
- [ ] USER Postman flow passes
- [ ] ADMIN Postman flow passes
- [ ] Full Overall collection passes
- [ ] Multiple Restaurant instances register
- [ ] Order continues working with one Restaurant instance stopped
- [ ] Eureka discovery verified independently of Postman URL changes

## Recommended validation order

    1. Eureka dashboard
    2. Login collection
    3. USER RBAC checks
    4. ADMIN functional baseline
    5. Order -> Restaurant happy path
    6. Restaurant 404 scenario
    7. Item 404 scenario
    8. JWT propagation
    9. Multiple Restaurant instances
    10. Overall collection
    11. Mark Phase 10 complete
