📌 Placement Management System

A microservices-based Placement Management System built using Spring Boot and Spring Cloud to manage students, companies, placements, and notifications in a scalable and secure way.

🏗 Microservices Architecture

The system consists of the following services:

🔹 Service Registry
ServiceRegistry (Eureka Server)
Used for service discovery and registration.
🔹 API Gateway
ApiGateway
Single entry point for all client requests.
Routes requests to respective microservices.
🔹 Authentication Service
Auth-Service
Handles user authentication.
Implements Spring Security + JWT.
🔹 Core Services
Student-Service → Manages student data
Company-Service → Manages company details
Placement-Service → Handles placement operations
Admin-Service / Admin-Server → Admin-related functionalities
Notification-Service → Sends notifications
🔐 Security Features
JWT-based authentication
Role-based access control
Secure REST APIs
Token validation via Gateway
🛠 Tech Stack
Java
Spring Boot
Spring Cloud
Spring Security
JWT
Eureka Server
API Gateway
Feign Client
Resilience4j (Circuit Breaker)
PostgreSQL
Docker
Maven
Git & GitHub
📂 Project Structure
PlacementSystem/
│
├── ServiceRegistry/
├── ApiGateway/
├── Auth-Service/
├── Student-Service/
├── Company-Service/
├── Placement-Service/
├── Admin-Service/
├── Admin-Server/
├── Notification-Service/
└── README.md
▶️ How to Run the Project
Step 1: Start Service Registry

Run ServiceRegistry (Eureka Server) first.

Step 2: Start Auth Service
Step 3: Start Other Microservices
Step 4: Start API Gateway

Access all services via the API Gateway URL.

🚀 Key Highlights
Fully modular microservices architecture
Centralized routing using API Gateway
Service discovery with Eureka
Secure authentication using JWT
Fault tolerance with Circuit Breaker
Container-ready (Docker supported)
📌 Future Improvements
Frontend integration (React/Angular)
CI/CD pipeline
Cloud deployment (AWS/Render)
Logging & monitoring (ELK stack)
Email integration
Role-based dashboards
