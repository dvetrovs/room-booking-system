# Room Booking System

A REST API for booking rooms, built with Java and Spring Boot. Users can be created, rooms can be listed, and bookings can be made, viewed and cancelled. Double-booking the same room for an overlapping time slot is rejected.

## Features

- Create and view **users**
- Create and view **rooms** (name, capacity, description)
- **Create a booking** for a room and time slot, with request validation and availability check (`409 Conflict` if the room is already booked)
- **View a user's bookings**
- **Cancel a booking**, allowed only for the user who owns it (`403 Forbidden` otherwise)
- Interactive API documentation with Swagger UI (OpenAPI)
- Database schema managed with Flyway migrations

## Tech stack

| Area | Technology |
| --- | --- |
| Language | Java 27 |
| Framework | Spring Boot 4.2.0 (Spring Web MVC) |
| Persistence | Spring Data JPA, PostgreSQL |
| Migrations | Flyway |
| Validation | Jakarta Bean Validation |
| API docs | springdoc-openapi (Swagger UI) |
| Build | Maven (Maven Wrapper included) |
| Local database | Docker Compose (PostgreSQL 16) |
| Other | Lombok |

## Getting started

### Prerequisites

- JDK 27 (the version set in `pom.xml`)
- Docker and Docker Compose

### 1. Start the database

```bash
docker compose up -d
```

This starts PostgreSQL 16 with:

| Setting | Value |
| --- | --- |
| Database | `booking` |
| User | `booking` |
| Password | `booking` |
| Host port | `5432` |

Data is kept in the `pgdata` Docker volume.

### 2. Run the application

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

Flyway applies the database migrations automatically on startup.

### 3. Open the API docs

Once the app is running, open Swagger UI (default springdoc path):

```
http://localhost:8080/swagger-ui.html
```

## API overview

### Users

| Method | Endpoint | Description | Responses |
| --- | --- | --- | --- |
| `POST` | `/api/users` | Create a user | `201`, `400`, `409` (email or username already exists) |
| `GET` | `/api/users` | List all users | `200` |
| `GET` | `/api/users/{id}` | Get a user by ID | `200`, `404` |

### Rooms

| Method | Endpoint | Description | Responses |
| --- | --- | --- | --- |
| `POST` | `/api/rooms` | Create a room | `201`, `400` |
| `GET` | `/api/rooms` | List all rooms | `200` |
| `GET` | `/api/rooms/{id}` | Get a room by ID | `200`, `404` |

### Bookings

| Method | Endpoint | Description | Responses |
| --- | --- | --- | --- |
| `POST` | `/api/bookings` | Create a booking | `201`, `400`, `409` (time slot already booked) |
| `GET` | `/api/bookings/my?userId={userId}` | List a user's bookings | `200`, `400` |
| `DELETE` | `/api/bookings/{id}?userId={userId}` | Cancel a booking | `204`, `403`, `404` |

The exact request and response bodies are documented in Swagger UI.

## Data model

- **User**: `id`, `name`, `email` (unique), `role` (defaults to `USER`)
- **Room**: `id`, `name` (unique), `capacity`, `description`, `active`
- **Booking**: `id`, `user`, `room`, `startTime`, `endTime`, `status` (`CONFIRMED` or `CANCELLED`), `createdAt`

Each booking belongs to one user and one room. Cancelling a booking sets its status to `CANCELLED` instead of deleting it.

## Project structure

```
src/main/java/com/example/room_booking_system/
├── controller/   REST controllers (users, rooms, bookings)
├── service/      Business logic
├── dto/          Request and response objects
└── entity/       JPA entities
```

## Author

Dmitrijs Vetrovs: [github.com/dvetrovs](https://github.com/dvetrovs)
