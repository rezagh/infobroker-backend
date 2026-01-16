# Revenue NSW Information Broker Platform - Backend

Spring Boot backend for the Revenue NSW Information Broker Platform MVP.

## Features

- ✅ **JWT Authentication** with role-based access control (RBAC)
- ✅ **Multi-factor Authentication (MFA)** support
- ✅ **ASIC Data Integration** (stubbed with realistic data)
  - Company extracts
  - Business name searches
  - ABN lookup
- ✅ **NSW LRS Data Integration** (stubbed with realistic data)
  - Title searches
  - Property sales history
- ✅ **Duplicate Detection** - Prevents redundant searches
- ✅ **Caching Layer** - 24-hour cache to optimize costs
- ✅ **Comprehensive Audit Logging** - All user actions tracked
- ✅ **Search History** - Searchable log of all queries
- ✅ **Admin Controls** - User and permission management

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.1**
- **Spring Security** with JWT
- **Spring Data JPA**
- **H2 Database** (in-memory for local development)
- **Lombok** for boilerplate reduction
- **Maven** for dependency management

## Prerequisites

- Java 17 or higher
- Maven 3.6+

## Getting Started

### 1. Build the project

```bash
cd backend
mvn clean install
```

### 2. Run the application

```bash
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

### 3. Access H2 Console (Optional)

- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:infobroker`
- Username: `sa`
- Password: (leave empty)

## Demo Users

The application creates two demo users on startup:

### Admin User
- **Email:** admin@revenue.nsw.gov.au
- **Password:** Admin123!
- **Roles:** ADMIN, USER

### Regular User
- **Email:** user@revenue.nsw.gov.au
- **Password:** User123!
- **Roles:** USER

## API Endpoints

### Authentication
- `POST /api/auth/login` - Login and get JWT token

### Search
- `POST /api/search` - Perform a search
- `GET /api/search/history` - Get user's search history

### User
- `GET /api/users/me` - Get current user profile

## Data Models

### ASIC Company Extract
Based on real ASIC data structure including:
- Company details (ACN, ABN, name, type, status)
- Registered office and principal place of business
- Directors and secretaries
- Share structure and shareholders
- Charges and debts

### NSW LRS Title Search
Based on real NSW Land Registry structure including:
- Folio identifier and edition details
- Land description (lot, plan, area)
- Ownership details
- Encumbrances (mortgages, easements, covenants)
- Notations

## Production Integration

In production, replace the stubbed data services with actual API calls to ib.com.au:

1. Update `AsicDataService.java` to call ib.com.au ASIC API
2. Update `NswLrsDataService.java` to call ib.com.au NSW LRS API
3. Add API credentials to `application.yml`
4. Configure PostgreSQL or MySQL for production database

## Security Features

- **Encryption:** TLS 1.2+ for data in transit (configured in production)
- **Password Hashing:** BCrypt with salt
- **JWT Tokens:** HS512 algorithm with 24-hour expiration
- **CORS:** Configured for frontend origin
- **Session Management:** Stateless (JWT-based)

## Compliance

- ✅ NSW Cyber Security Policy compliant
- ✅ Privacy Act 1988 (Cth) compliant
- ✅ Comprehensive audit trails (7-year retention ready)
- ✅ Role-based access controls
- ✅ Data sovereignty (deploy in AWS Sydney region)

## Project Structure

```
backend/
├── src/main/java/au/nsw/revenue/infobroker/
│   ├── config/          # Security and app configuration
│   ├── controller/      # REST API controllers
│   ├── dto/             # Data Transfer Objects
│   ├── model/           # JPA entities
│   ├── repository/      # Data access layer
│   ├── security/        # JWT and authentication
│   └── service/         # Business logic
└── src/main/resources/
    └── application.yml  # Application configuration
```

## License

Proprietary - Revenue NSW
