Bike Spare Parts Store Backend.

This project is the backend of a Bike Spare Parts Store application built using Spring Boot, Spring Security, and JWT for authentication. The backend provides APIs for managing bike spare parts, user authentication, role-based access, and order status management.

Features
JWT Authentication & Authorization: Secure authentication using JSON Web Tokens (JWT) with Spring Security.
Role-Based Access Control: Supports different user roles (Admin, User) with different access levels to application functionalities.
Order Management: Allows tracking and updating of order statuses (Pending, Shipped, Delivered).
Product Management: Admin users can manage spare parts (add, update, delete products).
CRUD Operations: Full Create, Read, Update, Delete functionality for bike spare parts and orders.
Database Integration: Integrated with MySQL to store user data, spare parts, and order information.
Technologies Used
Backend Framework: Spring Boot
Security: Spring Security, JWT (JSON Web Tokens)
Database: MySQL
Tools:
Postman (for API testing)
IntelliJ IDEA (IDE)
Setup Instructions
Prerequisites
Before running the project, ensure you have the following installed:

Java 17 or higher
Maven
MySQL
Steps to Set Up Locally
Clone the repository:

bash
Copy code
git clone https://github.com/Akash575795/Bike-spare-parts-store
cd bike-spare-parts-store-backend
Set up the MySQL database:

Create a database for the project.
Update application.properties or application.yml in src/main/resources with your database connection details (username, password, database name).
Example:

properties
Copy code
spring.datasource.url=jdbc:mysql://localhost:3306/bike_store_db
spring.datasource.username=root
spring.datasource.password=yourpassword
Build the application:

Using Maven:

bash
Copy code
mvn clean install
Run the application:

bash
Copy code
mvn spring-boot:run
The application should now be running on http://localhost:8080.
