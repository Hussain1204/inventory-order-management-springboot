# E-Commerce Backend System

A production-style backend application built using Java and Spring Boot,
designed to manage e-commerce operations using RESTful APIs and a layered architecture.

## Features
- RESTful APIs for product and inventory management
- CRUD operations using Spring Data JPA and Hibernate
- MySQL integration for persistent storage
- Layered architecture (Controller–Service–Repository)
- Exception handling and input validation
- API testing using Postman

## Tech Stack
- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Maven

## Architecture
The application follows a layered architecture to ensure clean separation of concerns,
scalability, and maintainability:

- **Controller Layer**: Handles HTTP requests and responses using RESTful endpoints
- **Service Layer**: Contains business logic and application workflows
- **Repository Layer**: Manages database interactions using Spring Data JPA
- **Entity Layer**: Defines domain models and database mappings

## Sample API Endpoints
- **GET** `/api/products` – Retrieve all products
- **POST** `/api/products` – Create a new product
- **PUT** `/api/products/{id}` – Update an existing product
- **DELETE** `/api/products/{id}` – Delete a product by ID

## Project Structure
- Controller layer for API endpoints
- Service layer for business logic
- Repository layer for database operations
- Entity layer for domain modeling

## How to Run
1. Clone the repository
2. Configure MySQL credentials in `application.yml`
3. Run the application:
   ```bash
   mvn spring-boot:run
