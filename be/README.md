# Moonstone Backend

A RESTful API backend for an event management platform, enabling cinema and event organization management.

## Tech Stack

- **Java 17** + **Spring Boot 3**
- **PostgreSQL** for data persistence
- **JWT** authentication with Access/Refresh tokens (HttpOnly cookies)
- **Gradle** build system
- **GitHub Actions** for CI/CD

## Security & Authentication

### JWT Token Architecture

| Token Type | Lifetime | Storage | Purpose |
|------------|----------|---------|---------|
| Access Token | 5-15 min | HttpOnly Cookie | API authorization |
| Refresh Token | days/weeks | HttpOnly Cookie | Token renewal |

### Authorization Flow

1. User authenticates via `/api/v1/auth/login`
2. Server validates credentials and issues AT + RT in cookies
3. AT is automatically sent with each protected request
4. When AT expires, client calls `/api/v1/auth/refresh-token` with RT
5. On logout (`/api/v1/auth/logout`), both cookies are cleared

### User Roles

- **ROLE_ADMIN** — Full admin rights: user/project/settings management
- **ROLE_USER** — Standard access: create and manage own entities

## API Endpoints

### Authentication

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/auth/login` | Login with email/password |
| POST | `/api/v1/auth/refresh-token` | Refresh access token |
| POST | `/api/v1/auth/logout` | Invalidate tokens |
| POST | `/api/v1/users/register` | Register new user |
| GET | `/api/v1/users/confirm/{code}` | Activate account |

### Cinemas

| Method | Endpoint | Role | Description |
|--------|----------|------|-------------|
| POST | `/api/v1/cinemas` | USER | Create cinema |
| GET | `/api/v1/cinemas` | USER | List all cinemas |
| GET | `/api/v1/cinemas/{id}` | USER | Get cinema by ID |
| DELETE | `/api/v1/cinemas/{id}` | ADMIN/Owner | Delete cinema |

### Events

| Method | Endpoint | Role | Description |
|--------|----------|------|-------------|
| POST | `/api/v1/events` | USER | Create event |
| GET | `/api/v1/events` | USER | List events (with filters) |
| GET | `/api/v1/events/{id}` | USER | Get event by ID |
| DELETE | `/api/v1/events/{id}` | ADMIN/Owner | Delete event |

### Geonames (City Search)

| Method | Endpoint | Role | Description |
|--------|----------|------|-------------|
| GET | `/api/v1/geonames` | USER | Search cities |
| GET | `/api/v1/geonames/{id}` | USER | Get city details |

### Administration

| Method | Endpoint | Role | Description |
|--------|----------|------|-------------|
| GET | `/api/v1/users/all` | ADMIN | List all users |

## Project Structure

```
src/main/java/com/moonstone/
├── config/           # Security, CORS, configuration
├── controller/       # REST controllers
├── dto/              # Request/Response DTOs
├── entity/           # JPA entities
├── exception/        # Custom exceptions & handlers
├── mapper/           # Entity <-> DTO mappers
├── repository/       # JPA repositories
├── security/         # JWT filters, auth helpers
└── service/          # Business logic
```

## Getting Started

### Prerequisites

- Java 17+
- PostgreSQL
- Gradle

### Environment Variables

```bash
DATABASE_URL=jdbc:postgresql://localhost:5432/moonstone
JWT_SECRET=your-secret-key
```

### Build & Run

```bash
./gradlew clean build
./gradlew bootRun
```

### Run Tests

```bash
./gradlew test
```

## CI/CD

Automated builds via GitHub Actions on:
- Push to `main`, `develop`
- Pull Requests to `main`, `develop`

Workflow: `.github/workflows/gradle.yml`
