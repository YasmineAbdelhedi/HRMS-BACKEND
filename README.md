# HRMS Backend (Spring Boot)

A Human Resource Management System backend built with Spring Boot.  
This API covers authentication, employee/user management, attendance, leave requests, payroll, project management, project assignments, and task tracking.


## Core Features

- JWT-based authentication (`/auth/signup`, `/auth/login`)
- Role/permission-based access control (`ADMIN`, `HR_MANAGER`, `PROJECT_MANAGER`, `EMPLOYEE`)
- User and profile management
- Attendance check-in and history queries by employee/date range
- Leave request workflow (create, approve, reject, filter by status)
- Payroll CRUD endpoints
- Project CRUD + assignment of employees to projects
- Task creation/assignment, employee task listing, status updates
- Email notifications for project/task assignment events
- Swagger/OpenAPI documentation with Bearer auth support

## Tech Stack

- **Language:** Java 17
- **Framework:** Spring Boot 3
- **Security:** Spring Security + JWT (`jjwt`)
- **Data Access:** Spring Data JPA (Hibernate)
- **Database:** MySQL
- **Build Tool:** Maven
- **API Docs:** springdoc OpenAPI + Swagger UI
- **Mail:** Spring Boot Mail (SMTP)

## Project Structure

```text
src/main/java/com/example/hrms
├── config          # Security, JWT filter, Swagger, beans
├── controller      # REST API endpoints
├── dto             # Request/response transfer objects
├── entity          # JPA domain models
├── exceptions      # Global exception handling
├── repository      # Spring Data repositories
├── response        # API response wrappers
└── service         # Business logic
```

## API Modules

- **Authentication:** user registration and login with JWT
- **Users/Profiles:** current user retrieval and profile CRUD
- **Attendance:** check-in and attendance history
- **Leave Requests:** create and approve/reject requests
- **Payroll:** payroll CRUD operations
- **Projects:** create/update/delete projects, assign employees
- **Project Assignments:** assignment CRUD
- **Tasks:** create/assign tasks, list tasks, update status, delete

## Getting Started

### 1) Prerequisites

- Java 17+
- Maven 3.8+
- MySQL 8+

### 2) Configure Environment

Update `src/main/resources/application.properties` with your local values:

- `spring.datasource.url`
- `spring.datasource.username`
- `spring.datasource.password`
- `security.jwt.secret-key`
- `spring.mail.*`

> Recommended for production: move sensitive values to environment variables or a secrets manager.

### 3) Run the Application

Using Maven wrapper:

```bash
./mvnw spring-boot:run
```

On Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

### 4) Access API Docs

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Security Notes

- API is stateless (`SessionCreationPolicy.STATELESS`) and uses Bearer JWT.
- Public routes include `/auth/**` and Swagger endpoints.
- Other routes require authentication and are protected through role/permission checks.

## Future Improvements

- Externalize all secrets to environment variables by default
- Add comprehensive unit/integration tests
- Add Docker setup for reproducible local development
- Add CI pipeline for build/test/deploy automation

## Author

**Yasmine Abdelhedi**  
HRMS Backend Project
