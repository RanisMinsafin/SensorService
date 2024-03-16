REST API Service for Sensor Data Reception
====================================================

This project represents a REST API service that receives data from a "sensor". The service is implemented using the Spring Framework.

Project Structure
-----------------

The project is divided into two main parts:

1.  **REST API Application:**
    *   Implements the reception and processing of data from sensors.
    *   Handles HTTP requests with data in JSON format.
    *   Provides endpoints for registering sensors, adding measurements, retrieving all measurements, and obtaining the number of rainy days.
2.  **Java Client:**
    *   Sends data to the REST API application using the RestTemplate class.
    *   Simulates the work of a real sensor by sending temperature and rain data to the server.

Endpoints
---------

*   **Register Sensor:**
    *   POST /sensors/registration - Registers a new sensor in the system.
*   **Add Measurement from Sensor:**
    *   POST /measurements/add - Adds a new measurement from a sensor.
*   **Get All Measurements:**
    *   GET /measurements - Returns all measurements from the database.
*   **Get Number of Rainy Days:**
    *   GET /measurements/rainyDaysCount - Returns the number of rainy days from the database.

Validation and Error Handling
-----------------------------

Data sent to the server is validated for compliance with format and constraints.

In case of errors, the server returns appropriate HTTP status codes and error messages.

Technologies
------------

*   Java
*   Spring Boot
*   Spring Data JPA
*   Hibernate-validator
*   Maven
*   RESTful API

Running the Project
-------------------

REST API Application:

1.  Run mvn spring-boot:run in the root directory of the project.

Java Client:

*   Use the main method in the Client.java class to send data to the server.
