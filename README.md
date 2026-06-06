# ☁️ Cloud Shop — Microservices Backend

A backend application built using **Java Spring Boot Microservices** architecture. The project simulates a real-world online shopping platform where each feature is handled by a separate, independently running service.

---

## 🧱 Architecture Overview

```
                        [ Client / Postman ]
                                |
                         [ API Gateway ]          ← Single entry point (Port 8080)
                                |
          ┌─────────────────────┼──────────────────────┐
          |                     |                      |
   [ User Service ]    [ Product Service ]    [ Order Service ]
   (Auth + JWT)         (Product CRUD)         (Place Orders)
                                                       |
                                             [ Payment Service ]
                                              (Process Payment)

          All services register with → [ Eureka Server ] (Service Discovery)
```

---

## 🔧 Microservices

| Service | Port | Description |
|---|---|---|
| **Eureka Server** | 8761 | Service registry — all services register here |
| **API Gateway** | 8080 | Single entry point — routes requests to correct service |
| **User Service** | 8081 | User registration, login, JWT authentication |
| **Product Service** | 8082 | Add, view, update, delete products |
| **Order Service** | 8083 | Place and manage orders, fetches product via Feign |
| **Payment Service** | 8084 | Process payments linked to orders via Feign |

---

## ⚙️ Tech Stack

- **Language:** Java
- **Framework:** Spring Boot
- **Microservices:** Spring Cloud (Eureka, API Gateway, Feign Client)
- **Security:** Spring Security, JWT Authentication
- **Database:** MySQL, H2 (Product Service)
- **ORM:** Spring Data JPA, Hibernate
- **API Testing:** Postman
- **Build Tool:** Maven
- **Version Control:** Git, GitHub

---

## 📁 Project Structure

```
Cloud-Shop/
│
├── eureka-server/          → Service registry (Eureka)
├── api-gateway/            → API Gateway (routes all requests)
├── user-service/           → Auth: Register, Login, JWT
├── product-service1/       → Product CRUD operations
├── order-service/          → Order management + Feign (calls Product)
└── payment-service/        → Payment processing + Feign (calls Order)
```

---

## 🔐 User Service — Features

- User Registration (`POST /auth/register`)
- User Login (`POST /auth/login`) — returns JWT token
- Get User Profile (`GET /user/profile`) — secured endpoint
- JWT Filter validates token on every request
- Password encrypted using BCrypt
- Global Exception Handling

**Key Classes:**
```
controller/  → AuthController, UserController
service/     → AuthService, JwtService, CustomUserDetailsService
entity/      → User
dto/         → RegisterRequest, LoginRequest, AuthResponse, UserProfileResponse
filter/      → JwtAuthFilter
config/      → SecurityConfig
exception/   → GlobalExceptionHandler
```

---

## 📦 Product Service — Features

- Add a new product (`POST /products`)
- Get all products (`GET /products`)
- Get product by ID (`GET /products/{id}`)
- Update product (`PUT /products/{id}`)
- Delete product (`DELETE /products/{id}`)

**Key Classes:**
```
controller/  → ProductController
service/     → ProductService
entity/      → Product
repository/  → ProductRepository
```

---

## 🛒 Order Service — Features

- Place a new order (`POST /orders`)
- Get order by ID (`GET /orders/{id}`)
- Fetches product details from Product Service using **Feign Client**

**Key Classes:**
```
controller/  → OrderController
service/     → OrderService
entity/      → Order
dto/         → ProductDTO
feign/       → ProductClient
repository/  → OrderRepository
```

---

## 💳 Payment Service — Features

- Process payment for an order (`POST /payments`)
- Get payment details (`GET /payments/{id}`)
- Fetches order details from Order Service using **Feign Client**

**Key Classes:**
```
controller/  → PaymentController
service/     → PaymentService
entity/      → Payment
dto/         → OrderDTO
feign/       → OrderClient
repository/  → PaymentRepository
```

---

## 🚀 How to Run

### Prerequisites
- Java 17+
- Maven
- MySQL
- IDE (IntelliJ / Eclipse)

### Steps

**1. Start Eureka Server first**
```bash
cd eureka-server
mvn spring-boot:run
```
Open: `http://localhost:8761`

**2. Start API Gateway**
```bash
cd api-gateway
mvn spring-boot:run
```

**3. Start all other services** (in any order)
```bash
cd user-service     && mvn spring-boot:run
cd product-service1 && mvn spring-boot:run
cd order-service    && mvn spring-boot:run
cd payment-service  && mvn spring-boot:run
```

**4. Test APIs via Postman**
All requests go through: `http://localhost:8080`

---

## 🔑 Sample API Calls

**Register User**
```
POST http://localhost:8080/auth/register
Body: { "name": "Sachin", "email": "sachin@gmail.com", "password": "1234" }
```

**Login**
```
POST http://localhost:8080/auth/login
Body: { "email": "sachin@gmail.com", "password": "1234" }
Response: { "token": "eyJhbGci..." }
```

**Add Product** *(use JWT token in header)*
```
POST http://localhost:8080/products
Headers: Authorization: Bearer <token>
Body: { "name": "Laptop", "price": 50000, "quantity": 10 }
```

**Place Order**
```
POST http://localhost:8080/orders
Body: { "productId": 1, "quantity": 2 }
```

---

## 👨‍💻 Author

**Sachin Waghmare**
- 📧 sachinwaghmare00731@gmail.com
- 🔗 [LinkedIn](https://linkedin.com/in/sachin-waghmare-556112406)
- 💻 [GitHub](https://github.com/sachin-dev45)

---

## 📌 Key Concepts Used

- Microservices Architecture
- Service Discovery (Eureka)
- API Gateway Pattern
- Inter-service Communication (Feign Client)
- JWT Authentication & Authorization
- REST API Design
- Spring Data JPA & Hibernate ORM
- DTO Pattern
- Global Exception Handling
