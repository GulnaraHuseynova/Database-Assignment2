# Database-Assignment2
Database/Assignment2
Overview:
This Java application is a simple inventory management system that interacts with a PostgreSQL database. It allows users to check stock availability, place orders, and updates book stock accordingly.

Prerequisites:
Before running the application, make sure you have the following installed:
Java Development Kit (JDK) - Download
PostgreSQL Database - Download

Setup Instructions:
1)Clone the Repository:
git clone https://github.com/your-username/inventory-management.git

2)Database Configuration:
Create a PostgreSQL database with the name inventory_db.
Execute the SQL scripts provided in the database_scripts folder to set up the necessary tables (Authors, Customers, Books, Orders).

3)JDBC Configuration:
Open the DatabaseCrudExample.java and TransactionExample.java files.
Update the JDBC_URL, USER, and PASSWORD variables with your PostgreSQL database connection details.

4)Compile the Application:
javac DatabaseCrudExample.java
javac TransactionExample.java

5)Run the Application:
java DatabaseCrudExample
java TransactionExample

6)Usage
Follow the on-screen prompts to interact with the application:
DatabaseCrudExample: Demonstrates basic CRUD operations on the Books table.
TransactionExample: Simulates a transaction involving checking stock availability, placing an order, and updating book stock.

