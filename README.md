# 🏦 Enterprise Banking System - Backend

A secure RESTful Banking Application built using Spring Boot, Spring Security, JWT Authentication, and MySQL. The backend provides APIs for authentication, account management, transactions, and analytics.

## 🚀 Live Demo

Backend API:
https://banking-backend-c2c0.onrender.com
---

# 🛠 Tech Stack

- Java 21
- Spring Boot 3
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- MySQL (Clever Cloud)
- Maven
- Docker
- Render

---

# Features

✔ User Registration

✔ Secure Login using JWT

✔ Role-Based Authentication

✔ Customer Dashboard APIs

✔ Admin Dashboard APIs

✔ Account Management

✔ Money Transfer

✔ Transaction History

✔ Analytics APIs

✔ Exception Handling

✔ REST Architecture

---

# Project Structure

src
 ├── config
 ├── controller
 ├── dto
 ├── entity
 ├── repository
 ├── security
 ├── service
 ├── exception
 ├── util
 └── resources

---

# Architecture

Client (React)

↓

REST API

↓

Spring Boot

↓

Spring Security + JWT

↓

Service Layer

↓

Repository Layer

↓

MySQL Database

---

# Database

MySQL hosted on Clever Cloud

Tables

- users
- roles
- accounts
- transactions

---

# Deployment

Backend : Render

Database : Clever Cloud

Container : Docker

---

# API Endpoints

POST /api/auth/register

POST /api/auth/login

GET /api/users/profile

POST /api/transactions/transfer

GET /api/transactions/history

...

---

# Security

JWT Authentication

Password Encryption

Role-Based Access Control

Spring Security

---

# Future Improvements

OTP Verification

Email Notifications

Refresh Tokens

PDF Statements

Loan Management

Microservices

Docker Compose

Kubernetes

CI/CD

AWS Deployment

---

# Author

Hemish S
