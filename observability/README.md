# FoodManagement Observability

## Purpose

This directory contains the local monitoring infrastructure for FoodManagement.

Current scope:
- Prometheus metrics collection
- Grafana visualization
- Docker Compose orchestration
- Provisioned Prometheus datasource
- Provisioned FoodManagement dashboard

Spring Boot application services continue to run locally. This directory does not containerize the application services.

## Directory

    observability/
    ├── docker-compose.yml
    ├── prometheus/
    │   └── prometheus.yml
    └── grafana/
        ├── provisioning/
        │   ├── datasources/prometheus.yml
        │   └── dashboards/dashboards.yml
        └── dashboards/foodmanagement-overview.json

## Ports

| Component | App | Management |
|---|---:|---:|
| API Gateway | 8080 | 9100 |
| Restaurant Management | 8001 | 9101 |
| User Management | 8002 | 9102 |
| Order Management | 8003 | 9103 |
| Delivery Management | 8004 | 9104 |
| Prometheus | - | 9090 |
| Grafana | - | 3000 |

## Start and validate

    docker --version
    docker compose version
    docker info
    cd observability
    docker compose config
    docker compose pull
    docker compose up -d
    docker compose ps

Prometheus:

    http://localhost:9090
    Status -> Targets

Expected: api-gateway, user-management, restaurant-management, order-management, delivery-management, and prometheus are UP.

Queries:

    up
    http_server_requests_seconds_count
    http_server_requests_seconds_bucket

Grafana:

    http://localhost:3000

Confirm the FoodManagement Prometheus datasource and the FoodManagement Observability Overview dashboard.

## Local service validation

For each management port:

    curl.exe -i http://localhost:<management-port>/actuator/health
    curl.exe -i http://localhost:<management-port>/actuator/prometheus

Expected: HTTP 200.

Generate successful and negative Postman traffic to populate request rate and error-rate panels. Confirm P95/P99 latency, service health, JVM heap, and CPU panels.

## Docker image workflow

Image = packaged template. Container = running instance of an image. docker push uploads an image to a registry; it does not push an image into a container.

Current upstream images:

    prom/prometheus:latest
    grafana/grafana:latest

Future application image flow, after application Dockerfiles exist:

    docker build -t <registry>/<namespace>/foodmanagement-<service>:<version> ./<service>
    docker login <registry>
    docker push <registry>/<namespace>/foodmanagement-<service>:<version>
    docker pull <registry>/<namespace>/foodmanagement-<service>:<version>

Full application image publication is part of the later Docker phase.

## Troubleshooting

Daemon unavailable:

    docker info

Prometheus target DOWN:

    curl.exe -i http://localhost:<management-port>/actuator/prometheus
    docker compose logs --tail=100 prometheus

Grafana datasource failure:

    http://prometheus:9090

Do not use localhost:9090 from inside the Grafana container.