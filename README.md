# Social Media Application

This is a simple social media application built using Spring Boot, Hibernate, and PostgreSQL, created using ChatGPT. 
It allows users to create and view posts, follow other users, and like posts. Each post has a title, body, and author. 
This README provides instructions on how to run the application and includes feedback for application development process using AI.

## Instructions

### Prerequisites

Before running the application, make sure you have the following installed:

- Java Development Kit 17 (JDK 17)
- PostgreSQL Database

### Setup

1. Clone the repository from GitHub: git clone <repository_url>
2. Create a PostgreSQL database and update the application properties in `src/main/resources/application.properties` with the database credentials.
3. Build and run the application: ./mvnw spring-boot:run

The application will be available at [http://localhost:8080](http://localhost:8080).

## Feedback

### 1. Was it easy to complete the task using AI?
It certainly boosted my productivity, but I needed to be very specific; otherwise, the output wasn't satisfying.  

### 2. How long did task take you to complete? (Please be honest, we need it to gather anonymized statistics)
Around 5 hours.

### 3. Was the code ready to run after generation? What did you have to change to make it usable?
No, it was not. Although AI is helpful with writing code, it sometimes has problems with integrating different components. For example, it writes tests for previously written classes that are failing, or it wants to create a table named 'User,' although it's not possible in PostgreSQL because it's a reserved keyword.

### 4. Which challenges did you face during completion of the task?
The main challenges I faced during the task included the need to be very specific. If I don't explicitly instruct the AI to handle validation, it won't do it, even though it might be obvious to me that it needs to be included in code. Other examples of poorly generated code due to a lack of my clarification were: the absence of Data Transfer Objects (DTOs) and field-based Dependency Injection.

### 5. Which specific prompts you learned as a good practice to complete the task?
I found that a good practice is to ask the AI to generate code based on examples found on the internet.
