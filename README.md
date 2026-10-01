# Student Management System

Student Management System is a web-based application developed to manage student records efficiently. The system allows users to perform basic student management operations such as adding, viewing, updating, and deleting student information.

## Features

* Add new student
* View student details
* Update student information
* Delete student records
* Manage student data efficiently
* CRUD operations

## Technologies Used

* Java
* Spring Boot
* Spring Data JPA
* MySQL
* HTML
* CSS
* JavaScript

## Project Structure

```text
StudentManagementSystem
├── src
│   ├── main
│   │   ├── java
│   │   └── resources
│   └── test
├── pom.xml
└── README.md
```

## Database

The project uses **MySQL** to store and manage student information.

Example database configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_management
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## How to Run the Project

### 1. Clone the Repository

```bash
git clone https://github.com/SST1303/StudentsManagementSystem
```

### 2. Open the Project

Open the project in **Eclipse**, **IntelliJ IDEA**, or **Spring Tool Suite**.

### 3. Configure MySQL

Create a MySQL database:

```sql
CREATE DATABASE student_management;
```

Update the database username and password in `application.properties`.

### 4. Run the Application

Run the Spring Boot application from the main application class.

The application will start on:

```text
http://localhost:8080
```

## CRUD Operations

| Operation | Description                         |
| --------- | ----------------------------------- |
| Create    | Add a new student                   |
| Read      | View student details                |
| Update    | Update existing student information |
| Delete    | Delete a student record             |

## Future Enhancements

* Student search functionality
* Pagination
* User authentication and authorization
* Student profile management
* Responsive user interface
* Deployment to cloud

## Author

**Shraddha Thorat**

GitHub: [SST1303](https://github.com/SST1303)
