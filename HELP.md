# Getting Started

### Service description
You are looking on implementation of REST API counter service. 

It uses:
* Java 11
* Spring Boot
* Maven

Supported media types: 
* application/json

### How to start

Start it as a Spring Boot application: ./mvnw spring-boot:run
To start the tests run: ./mvnw test

### Authorization
To use the service you need to get the bearer token - send POST request on 'http://localhost:8080/user' with parameters:

user: admin
password: admin

or use curl request:

```
curl --location --request POST 'http://localhost:8080/user' \
--form 'user="admin"' \
--form 'password="admin"'
```

For all further requests use your bearer token in 'Authorization' header.

### Service specification
* OpenAPI v3 JSON specification you can find here: http://localhost:8080/v3/api-docs/ ('Authorization' header is required)
* Swagger UI is available here: http://localhost:8080/swagger-ui.html ('Authorization' header is required)
