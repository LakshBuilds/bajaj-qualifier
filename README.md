# Bajaj Finserv Health Qualifier - Spring Boot Application

This is a Spring Boot application built for the Bajaj Finserv Health qualifier round. The application automatically processes a webhook flow on startup, solves SQL problems, and submits solutions.

## Features

- **Automatic Webhook Generation**: Sends POST request to generate webhook on application startup
- **SQL Problem Solving**: Determines and solves SQL problems based on registration number
- **Database Storage**: Stores SQL results in H2 in-memory database
- **JWT Authentication**: Uses JWT tokens for secure API communication
- **Solution Submission**: Automatically submits final SQL query to webhook URL

## Technology Stack

- **Spring Boot 3.2.0**
- **Java 17**
- **Spring Data JPA**
- **H2 Database**
- **RestTemplate**
- **JWT (JSON Web Tokens)**
- **Maven**

## Project Structure

```
src/main/java/com/bajajfinserv/healthqualifier/
├── HealthQualifierApplication.java    # Main Spring Boot application
├── config/
│   └── RestTemplateConfig.java        # RestTemplate configuration
├── dto/
│   ├── SolutionRequest.java           # Solution submission DTO
│   ├── WebhookRequest.java            # Webhook generation DTO
│   └── WebhookResponse.java           # Webhook response DTO
├── entity/
│   └── SqlResult.java                 # Database entity for SQL results
├── repository/
│   └── SqlResultRepository.java       # JPA repository
└── service/
    ├── SqlProblemSolver.java          # SQL problem solving logic
    └── WebhookService.java            # Main webhook orchestration service
```

## How It Works

1. **Application Startup**: The application triggers the webhook flow automatically on startup using `@EventListener(ApplicationReadyEvent.class)`

2. **Webhook Generation**: Sends a POST request to `https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA` with registration details

3. **Problem Determination**: Based on the last two digits of the registration number:
   - **Odd numbers**: Question 1
   - **Even numbers**: Question 2

4. **SQL Solution**: Generates the appropriate SQL query based on the question type

5. **Database Storage**: Stores the result in the H2 database

6. **Solution Submission**: Submits the final SQL query to the webhook URL using JWT authentication

## Setup and Running

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Building the Application

```bash
mvn clean package
```

### Running the Application

```bash
java -jar target/health-qualifier-1.0.0.jar
```

Or using Maven:

```bash
mvn spring-boot:run
```

### Accessing H2 Console

Once the application is running, you can access the H2 database console at:
```
http://localhost:8080/h2-console
```

Database details:
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: `password`

## Configuration

The application uses the following configuration in `application.properties`:

- **H2 Database**: In-memory database for storing SQL results
- **JPA**: Automatic table creation and SQL logging enabled
- **Logging**: Debug level logging for the application package
- **Server**: Runs on port 8080

## API Endpoints

The application doesn't expose any REST endpoints as per requirements. All processing happens automatically on startup.

## Logging

The application provides detailed logging for:
- Webhook generation process
- SQL problem solving
- Database operations
- Solution submission
- Error handling

## Customization

To modify the registration details, update the `generateWebhook()` method in `WebhookService.java`:

```java
WebhookRequest request = new WebhookRequest("Your Name", "Your Reg No", "your.email@example.com");
```

To update SQL solutions, modify the `solveQuestion1()` and `solveQuestion2()` methods in `SqlProblemSolver.java`.

## Error Handling

The application includes comprehensive error handling:
- HTTP request failures
- Database operation errors
- JWT token issues
- General exceptions

All errors are logged with appropriate details for debugging.

## Build Output

The application generates a JAR file at `target/health-qualifier-1.0.0.jar` that can be deployed and run independently.

## Notes

- The application uses placeholder SQL queries. You need to replace them with actual solutions based on the provided Google Drive links
- The registration number "REG12347" is used as an example. Update it with your actual registration number
- The application automatically handles JWT token management for API authentication 