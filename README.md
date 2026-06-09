# Membership Program

Backend system for managing membership plans, tiers, subscriptions, and configurable member benefits.

## Tech Stack

- Java 17
- Spring Boot 4
- Spring Data JPA
- MySQL
- Maven
- Springdoc OpenAPI (Swagger)

## Features

- Membership Plans (Monthly, Quarterly, Yearly)
- Membership Tiers (Silver, Gold, Platinum)
- Subscription Management
- Upgrade / Downgrade Membership Tier
- Cancel Subscription
- Configurable Benefits per Tier
- Tier Evaluation via Strategy Pattern (Order Count, Order Value, Cohort)
- Optimistic Locking for Concurrency

## API Documentation

Once the application is running, Swagger UI is available at:

```
http://localhost:8080/swagger-ui/index.html
```

## APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/plans` | Get all membership plans with tiers and benefits |
| POST | `/api/plans/evaluate-tier` | Get recommended tier based on user activity |
| POST | `/api/subscriptions` | Subscribe to a plan and tier |
| GET | `/api/subscriptions/{userId}` | Get current subscription and expiry |
| PUT | `/api/subscriptions/{userId}/upgrade` | Upgrade to next tier |
| PUT | `/api/subscriptions/{userId}/downgrade` | Downgrade to previous tier |
| PUT | `/api/subscriptions/{userId}/cancel` | Cancel subscription |

## Setup

### Prerequisites
- Java 17
- MySQL

### Steps

1. Create the database
```sql
CREATE DATABASE membership_db;
```

2. Update credentials in `src/main/resources/application.yml`
```yaml
datasource:
  username: your_username
  password: your_password
```

3. Run the application
```bash
./mvnw spring-boot:run
```

The app will auto-seed plans, tiers, and benefits on first startup.
