# Event Management System API

## Overview

The **Event Management System** is a Spring Boot-based RESTful API designed to manage events, user authentication, and RSVPs. It follows a modular project structure, implements custom exception handling, and uses SQLite as the database.

## Features Implemented

- **User Authentication**: Registration and authentication of users.
- **Event Management**: Create, update, delete, and fetch events.
- **RSVP System**: Users can RSVP to events.
- **Custom Exception Handling**: Graceful handling of errors and exceptions.
- **Swagger API Documentation**: Integrated OpenAPI (Swagger) for API documentation.
- **Modular Architecture**: Structured codebase for scalability and maintainability.
- **SQLite Database**: Lightweight database for easy storage and retrieval.

## Technologies Used

- **Spring Boot** (REST API development)
- **Spring Security** (for authentication and authorization)
- **SQLite** (database)
- **Swagger (OpenAPI 3.0)** (API documentation)
- **Lombok** (reducing boilerplate code)
- **JUnit & Mockito** (testing)

## API Documentation

The API is well-documented using Swagger. You can access it at:

- Development: `http://localhost:8080/swagger-ui.html`
- Production: `https://api.example.com/swagger-ui.html`

## Project Structure

```
com.system.event_management
│── config         # Configuration files (Swagger, Security, etc.)
│── controller     # API controllers (EventController, UserController)
│── exception      # Custom exception handling
│── model          # Data models (EventRequestBean, UserRequestBean, etc.)
│── repository     # Data access layer
│── service        # Business logic (EventService, UserService)
│── util          # Utility classes
│── main class     # Application entry point
```

## How to Run

### Prerequisites

- Java 17+
- Gradle
- SQLite installed (or use in-memory mode)

### Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/event-management-system.git
   cd event-management-system
   ```
2. Build and run the application:
   ```bash
   ./gradlew clean build
   ./gradlew bootRun
   ```
3. The API will be available at `http://localhost:8080`.

## Security

The API uses **JWT Authentication** for secure access.

- **Bearer Token** must be passed in the `Authorization` header.
- Authentication endpoints:
    - **POST** `/auth/register` - Register a new user.
    - (Future Enhancement) **POST** `/auth/login` - Login to get a JWT token.

## Error Handling (Custom Exceptions)

The project includes a robust error-handling mechanism using `@RestControllerAdvice`. Common exceptions handled include:

- **Invalid Event ID Format** (`InvalidEventIDException`)
- **Event Not Found** (`EventNotFoundException`)
- **User Exception** (`UserException`)

## API Endpoints

### User Authentication

| Method | Endpoint         | Description         |
| ------ | ---------------- | ------------------- |
| POST   | `/auth/register` | Register a new user |

### Event Management

| Method | Endpoint       | Description        |
| ------ | -------------- | ------------------ |
| GET    | `/events`      | Get all events     |
| POST   | `/events`      | Create a new event |
| PUT    | `/events/{id}` | Update an event    |
| DELETE | `/events/{id}` | Delete an event    |

### RSVP Management

| Method | Endpoint                 | Description      |
| ------ | ------------------------ | ---------------- |
| POST   | `/events/{eventId}/rsvp` | RSVP to an event |

## Future Enhancements

- User Login & JWT authentication
- Role-based access control
- Email notifications for RSVPs
- Event filtering and search functionality

## License

This project is licensed under the **Apache 2.0 License**. See [LICENSE](https://www.apache.org/licenses/LICENSE-2.0.html) for details.

## Contact

For any issues, contact **Developer Support**:

- **Website**: [Portfolio](https://deependra-trivedi-portfolio.vercel.app/)
- **Email**: [deependra04trivedi@gmail.com](mailto\:deependra04trivedi@gmail.com)



