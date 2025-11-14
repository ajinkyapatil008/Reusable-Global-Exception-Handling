# 🌐 Reusable Global Exception Handling Starter

A reusable **Spring Boot Starter** that provides a **centralized, production-grade global exception handling module** for microservices.  
It ensures consistent error responses, proper logging with traceId, and easy plug-and-play integration with any Spring Boot application.

This module eliminates repetitive exception handling code in individual microservices and replaces it with a clean, uniform, configurable solution.

---

# 🚀 Key Features

### ✔ Centralized Global Exception Handling  
Automatically handles all major exceptions thrown in your application.

### ✔ Standard JSON Error Response Format  
Every error returns the same structure:

json
{
  "timestamp": "2025-01-01T10:20:20.345Z",
  "status": 404,
  "error": "Not Found",
  "message": "User with ID 999 not found",
  "path": "/api/users/999",
  "traceId": "e41d1ef0-32fb-4f65-bf1b-4cf2f85b8039"
}

✔ Built-in Trace ID Support

Each request gets a unique traceId (MDC), automatically added to logs.

✔ Works as a Spring Boot Starter

Just add a dependency and annotate your main class — no manual setup required.

✔ Ready-to-use Custom Exceptions
      ResourceNotFoundException
      InvalidRequestException
      BusinessException
      UnauthorizedAccessException
      BadRequestException
      ForbiddenAccessException
      ConflictException
      TooManyRequestsException
      ServiceUnavailableException

✔ Auto Configuration

Spring Boot auto-detects and loads this module through:

  META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports


📦 1. How to Install the Starter on Your System

Clone or download the starter project, then run:

    mvn clean install

This installs the JAR into your local Maven repository:

    ~/.m2/repository/com/company/common/exception/global-exception-handling-starter/0.0.1-SNAPSHOT/

🔌 2. How to Integrate the Starter into Any Spring Boot Project

Open your demo app’s pom.xml, and add:

    <dependency>
        <groupId>com.company.common.exception</groupId>
        <artifactId>global-exception-handling-starter</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>

🧩 3. Enable the Global Exception Handler

In your main Spring Boot class:

    @EnableGlobalExceptionHandler
    @SpringBootApplication
    public class DemoApplication {
        public static void main(String[] args) {
            SpringApplication.run(DemoApplication.class, args);
        }
    }

✔ That’s it — the starter is now active
✔ No additional configuration required

🛠 4. How to Use Custom Exceptions

Throw them from your controllers/services:

Example: Not Found

    if (user == null) {
        throw new ResourceNotFoundException("User not found");
    }

Example: Bad Request

    throw new InvalidRequestException("Invalid age provided");

Example: Forbidden

    throw new ForbiddenAccessException("Not allowed to access this resource");

Example: Conflict

    throw new ConflictException("Email already registered");

📘 5. Example Controller for Testing

    @RestController
    @RequestMapping("/api/users")
    public class UserController {
    
        @GetMapping("/{id}")
        public ResponseEntity<String> getUser(@PathVariable int id) {
    
            if (id == 999)
                throw new ResourceNotFoundException("User with ID 999 not found");
    
            if (id == 0)
                throw new InvalidRequestException("Invalid ID");
    
            return ResponseEntity.ok("User " + id);
        }
    }

🔍 6. Example Outputs

  👉 GET /api/users/999
  
    {
      "timestamp": "2025-01-01T10:20:20.345Z",
      "status": 404,
      "error": "Not Found",
      "message": "User with ID 999 not found",
      "path": "/api/users/999",
      "traceId": "bd4321c1-21ab-4559-94f2-45839431b2bb"
    }

  👉 GET /api/users/0
  
    {
      "timestamp": "...",
      "status": 400,
      "error": "Bad Request",
      "message": "Invalid ID",
      "path": "/api/users/0",
      "traceId": "..."
    }

🧪 7. How to Run Tests (JUnit + MockMvc)

  Create this test:
  
      @WebMvcTest(UserController.class)
      @Import(GlobalExceptionAutoConfiguration.class)
      public class UserControllerTest {
      
          @Autowired
          private MockMvc mvc;
      
          @Test
          void testUserNotFound() throws Exception {
              mvc.perform(get("/api/users/999"))
                  .andExpect(status().isNotFound())
                  .andExpect(jsonPath("$.message").value("User with ID 999 not found"));
          }
      }

  Run:
  
    mvn test

  🏗 8. Architecture Diagram
  
                 ┌────────────┐
                 │   Client   │
                 └──────┬─────┘
                        │ HTTP Request
                        ▼
              ┌────────────────────┐
              │     Controller     │
              └─────────┬──────────┘
                        │ throws exception
                        ▼
       ┌────────────────────────────────────┐
       │   GlobalExceptionHandler (Starter) │
       ├────────────────────────────────────┤
       │   Builds ErrorResponse (JSON)      │
       │        Adds traceId                │
       │     Maps HTTP status codes         │
       └────────────────┬───────────────────┘
                        │ JSON Response
                        ▼
                 ┌────────────┐
                 │   Client   │
                 └────────────┘
🧱 9. Project Structure

    starter/
     ├── pom.xml
     ├── src/main/java/com/company/common/exception/
     │     ├── annotation/EnableGlobalExceptionHandler.java
     │     ├── config/GlobalExceptionAutoConfiguration.java
     │     ├── handler/GlobalExceptionHandler.java
     │     ├── exception/*.java
     │     ├── model/ErrorResponse.java
     │     └── util/TraceIdFilter.java
     └── src/main/resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports

🧾 10. Troubleshooting

             Issue	                          Cause	                          Solution
             
    package does not exist	           Starter JAR not installed	     Run mvn clean install in starter
    Handler not triggered	           Missing annotation	             Add @EnableGlobalExceptionHandler
    traceId not appearing	           logback not configured            Add logback-spring.xml
    Validation errors not caught	   Missing dependency	             Add spring-boot-starter-validation

    <br>
  <br>

  # 🧠  API Endpoints

### URL for POSTMAN Documentation :  <br>
https://documenter.getpostman.com/view/28841961/2sB3WvMyKp

<br>

Exception cases considered for this project:

     • BadRequestException
     • BusinessException
     • ConflictException
     • ForbiddenAccessException
     • InvalidRequestException
     • ResourceNotFoundException
     • ServiceUnavailableException
     • TooManyRequestsException
     • UnauthorizedAccessException
     <br>
  <br>

📈 Future Enhancements


