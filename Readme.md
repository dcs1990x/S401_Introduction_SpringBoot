# Task S4.01 – Introduction to Spring Boot

## 📋 Project Overview
The goal of this project is to build your first REST API using Spring Boot, learning the fundamental concepts of REST architecture, controller creation, HTTP methods, JSON serialization, and testing.
This exercise serves as an introduction to backend development with Spring Boot, focusing on good practices, clean architecture, and test-driven development (TDD).

The final API allows to:

- Verify the application's status through a /health endpoint.   
- Manage an in-memory list of users with CRUD-like operations.   
- Apply basic layered architecture principles (Controller, Service, Repository).   

## 💻 Technologies Used

- Java 21   
- Maven   
- Spring Boot 3.5.7  
- Spring Web   
- Spring Boot DevTools   
- JUnit 5   
- Mockito   

## 👥 User Manager API
A minimal REST API that manages users in memory, exposing endpoints for listing, creating, retrieving, and filtering users.   

This project progresses through three levels:

1. Creating a health-check endpoint.
2. Managing users in memory.
3. Refactoring to a layered architecture (Controller, Service, Repository).

## 📦 Project Structure
The final project follows the MVC layered architecture, organized as follows:

cat.itacademy.s04.t01.level3 
├── controllers   
│   ├── HealthController.java   
│   └── UserController.java   
├── entities
│   └── User.java   
├── repository   
│   ├── UserRepository.java   
│   └── InMemoryUserRepository.java    
└── UserNotFoundException.java   

## 🧩 Main Components

### 🩺 HealthController

A simple REST controller that defines the health check endpoint:
```
@RestController
public class HealthController {

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "OK");
    }
}
```

### ✅ HealthController (Level 1):   

GET /health → Returns { "status": "OK" }    

### 🌐 UserController Levels 2 and 3)   

Defines the REST API endpoints to manage users:

Method	   Endpoint	          Description   
GET	    /users	            Retrieve all users   
GET	    /users/{id}	        Retrieve a user by ID   
GET	    /users?name={name}	Filter users by name   
POST	/users	            Create a new user   

Example request:   
```
{
"name": "Ada Lovelace",
"email": "ada@example.com"
}
```

### 👤 User (Model)

Represents a user entity managed in memory:
```
public class User {
    private UUID id;
    private String name;
    private String email;
}
```
### 🧠 UserRepository (Interface)

Defines the contract for user data access operations:
```
public interface UserRepository {
    User save(User user);
    List<User> findAll();
    Optional<User> findById(UUID id);
    List<User> searchByName(String name);
    boolean existsByEmail(String email);
}
```
### 💾 InMemoryUserRepository (Implementation)

Simulates a persistent store using an internal list as in-memory storage.

## 🧠 User Stories & Acceptance Criteria

Health Endpoint:   

✅ GET /health returns { "status": "OK" }   
⚠️ Returns 404 if endpoint is misconfigured.   

Create User:   

✅ Returns 200 OK with created user (including UUID).   
⚠️ Returns 400 Bad Request if email already exists.   

Retrieve Users:   

✅ GET /users returns JSON array of users (empty if none).   
✅ GET /users/{id} returns the correct user if found.   
⚠️ Returns 404 if user not found.   

Filter Users:   

✅ GET /users?name=ada filters case-insensitively by name.   

## ⚙️ Project Configuration

src/main/resources/application.properties
```
spring.application.name=user-api
server.port=9000
```
## 🚀 How to Run

1️⃣ Clone the repository   
```
git clone https://github.com/dcs1990x/S401_Introduction_SpringBoot.git    
```
2️⃣ Build the project   
```
mvn clean package -DskipTests
```
3️⃣ Run the application   
```
java -jar target/userapi-0.0.1-SNAPSHOT.jar
```
4️⃣ Access the API

Health Check: 
```
http://localhost:9000/health
```
User List: 
```
http://localhost:9000/users
```
## 🧪 Testing

The project includes unit tests and integration tests using JUnit 5 and MockMvc.

HealthControllerTest: Verifies /health returns { "status": "OK" }.

UserControllerTest: Ensures user endpoints behave correctly:
- GET /users returns an empty list initially.
- POST /users adds a new user and returns it.
- GET /users/{id} returns the correct user or 404 if not found.
- GET /users?name= filters results properly.
- UserServiceImplTest (with Mockito)
- Verifies the service layer’s logic:
- Prevents duplicate emails.
- Generates UUIDs for new users.
- Calls repository methods correctly.

## 👨‍💻 Author

Developed by Daniel Caldito Serrano as part of the Java Back-end Development Bootcamp organized by IT Academy.