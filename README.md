# Banking Application with Microservices Architecture

## Introduction

This project is an MVP(minimum viable product) for a banking application built using Microservices architecture. It consists of two main microservices: Customer Management Service and Account Management Service, along with other components such as API Gateway, Eureka server for service registration, and centralized configuration management.

## Features

### Customer Management Service

- **Add Customer**: Add a new customer to the system.
- **Get all Customers**: Retrieve a list of all customers.
- **Get single Customer Details**: Retrieve details of a single customer by their ID.
- **Update Customer Details**: Update existing customer details.
- **Delete Customer**: Delete a customer, which also deletes their associated account from the Account Management Service.

### Account Management Service

- **Add Money to Account**: Add money to a customer's account. Before adding money, validate customer details.
- **Withdraw Money from Account**: Withdraw money from a customer's account. Before withdrawing money, validate customer details.
- **Get Account Details**: Retrieve details of a customer's account, including customer details.
- **Delete Account**: Delete a customer's account.

### Other Components

- **API Gateway**: Route requests from clients to appropriate microservices.
- **Eureka Server**: Service registration and discovery for microservices.
- **Centralized Configuration Management**: Manage configurations for microservices centrally.

## Technology Used

- Java Spring Boot: Backend framework for building microservices.
- Spring Cloud: For implementing features like service discovery and centralized configuration management.
- Netflix Eureka: For service registration and discovery.
- Maven: Dependency management and build tool.

## Prerequisites

- Java JDK installed on your local machine.
- Maven installed for building and managing dependencies.
- Basic understanding of Java Spring Boot and Microservices architecture.


## For the Local Setup Guide and Sample URLs, please refer to the documentation folder on GitHub.
