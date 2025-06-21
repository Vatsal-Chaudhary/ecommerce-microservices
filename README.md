# E-commerce Microservices Application

A scalable, secure, and fault-tolerant e-commerce backend built using microservices architecture. This project demonstrates the integration of various technologies to create a robust system for managing users, products, orders, and authentication.

## **Key Features**

### **Scalability**
- **Microservices Architecture**: Independent services for user management, product catalog, and order processing, enabling horizontal scaling.
- **Database Design**: Utilizes MongoDB for NoSQL storage and PostgreSQL for relational data, ensuring efficient data handling and scalability.
- **Containerization**: Docker Compose simplifies deployment and scaling across multiple environments.

### **Security**
- **Authentication & Authorization**: Keycloak integration for secure OAuth2-based authentication and role-based access control.
- **JWT Validation**: Spring Security with reactive JWT decoding ensures secure communication between services.
- **Environment Isolation**: Sensitive data is managed using environment variables and external configuration files.

### **Fault Tolerance**
- **Resilience4j Integration**: Circuit breakers and retry mechanisms are implemented to handle service failures gracefully.
- **Health Monitoring**: Configured health endpoints for monitoring service health and ensuring system reliability.
- **Service Discovery**: Eureka Server dynamically registers and discovers services, ensuring high availability.

### **Kafka Usability**
- **Message Streaming**: Apache Kafka is used for asynchronous communication between services, enabling real-time data processing.
- **Event-Driven Architecture**: Kafka topics are utilized for publishing and consuming events, ensuring decoupled and efficient inter-service communication.

## **Technologies Used**
- **Languages**: Java
- **Frameworks**: Spring Boot, Spring Cloud Gateway, Spring Security
- **Message Broker**: Apache Kafka
- **Databases**: MongoDB, PostgreSQL
- **Containerization**: Docker Compose
- **Monitoring**: Resilience4j, Spring Actuator

## **Setup**
Refer to the `README.md` for detailed instructions on running the application locally using Docker Compose.

## **Architecture Overview**
- **API Gateway**: Centralized routing and security using Spring Cloud Gateway.
- **Microservices**: Independent services for user, product, and order management.
- **Message Streaming**: Kafka for real-time event processing.
- **Configuration Management**: Spring Cloud Config Server for environment-specific settings.