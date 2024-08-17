# Entity Relationship Diagram

![Screenshot 2024-08-16 220118](https://github.com/user-attachments/assets/0f309ecd-5c9c-44a7-b3b1-cf8915cae0f2)

# Library Management Application

A web-based application for managing library resources, including books, patrons, and loans.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Endpoints](#endpoints)
- [Authentication and Authorization](#authentication-and-authorization)
- [Security](#security)
- [Technical Requirements](#technical-requirements)
- [Installation](#installation)
- [Usage](#usage)
- [API Documentation](#api-documentation)
- [Acknowledgments](#acknowledgments)


## Overview

The Library Management Application is a Spring Boot-based web application designed to manage library resources. The application provides a user-friendly interface for librarians to manage books, patrons, and loans.

## Features

* **Book Management**:
	+ Add new books
	+ Update existing books
	+ Delete books
	+ Search for books by ID
* **Patron Management**:
	+ Add new patrons
	+ Update existing patrons
	+ Delete patrons
	+ Search for patrons by ID
* **Borrowing Management**:
	+ Check out books to patrons
	+ Return books
	+ Borrow books
	+ View loan history
* **User Authentication and Authorization**:
	+ Log in functionality

## Endpoints

* **Book Endpoints**:
	+ GET /books: Get all books
	+ GET /books/{id}: Get a book by ID
	+ POST /books: Create a new book
	+ PUT /books/{id}: Update a book
	+ PATCH /books/{id}: Update a book
	+ DELETE /books/{id}: Delete a boo

* **Patron Endpoints**:
	+ GET /patrons: Get all patrons
	+ GET /patrons/{id}: Get a patron by ID
	+ POST /patrons: Create a new patron
	+ PUT /patrons/{id}: Update a patron
	+ DELETE /patrons/{id}: Delete a patron

* **Borrowing Endpoints**:
	+ GET /borrowings: Get all borrowings
	+ GET /borrowings/{id}: Get a borrowing by ID
	+ POST /borrowings: Borrow a book
	+ PUT /borrowings/{id}: Return a book

## Authentication and Authorization

The API uses JWT authentication and authorization. To use the API, you need to obtain a JWT token by sending a POST request to the /login endpoint with a valid username and password.

* POST /login: Get a JWT token

## Security

The API uses HTTPS encryption to secure data transmission. The API also uses JWT authentication and authorization to ensure that only authorized users can access and modify data.

## Technical Requirements

* Java 11 or later
* Spring Boot 2.3.0 or later
* MySQL 8.0 or later
* Maven 3.6.0 or later

## Installation

To install the application, follow these steps:

1. Clone the repository: `git clone https://github.com/seifmotaglly/Library-Management-System.git`
2. Change into the project directory: `cd Library-Management-System`
3. Build the project: `mvn clean package`
4. Create a MySQL database:`//localhost:3306/libraryDB`
5. Create a MySQL user and password: `mysql -u root -p root < libraryDB.sql`
6. Run the application: `java -jar target/Library-Management-0.0.1-SNAPSHOT.jar`

## Usage

To use the application, follow these steps:

1. You need to register 
2. Activate your account by entering the code sent to your email
4. Log in with your credentials

## API Documentation

The application provides a RESTful API for interacting with the library data. API documentation can be found at http://localhost:8080/swagger-ui.html after starting the application.

## Acknowledgments

* Spring Boot and Spring Data JPA for providing a robust framework for building the application
* MySQL for providing a reliable database solution
* Maven for providing a convenient build and dependency management tool
* Email Serivce for providing a convenient way to send emails to users to activate accounts
* Swagger for providing API documentation
* Bootstrap for providing a responsive and mobile-friendly user interface
