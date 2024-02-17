# Weather API Application

This repository contains a simple REST API application that provides weather information based on the city.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
- [Endpoints](#endpoints)
- [Configuration](#configuration)
- [Contributing](#contributing)

## Introduction

This is a basic REST API application built using [Spring Boot](https://spring.io/projects/spring-boot) framework. The application allows users to retrieve weather information for a specific city by making HTTP requests to predefined endpoints.

## Features

- Get weather information for a specific city.
- Store weather data in a database.

## Technologies Used

- [Spring Boot](https://spring.io/projects/spring-boot): Web framework for building the REST API.
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa): Data access framework for interacting with the database.
- [H2 Database](https://www.h2database.com/): Embedded database for local development.
- [OpenWeatherMap API](https://openweathermap.org/api): External API for weather data.

## Getting Started

### Prerequisites

Make sure you have the following installed:

- [Java](https://www.oracle.com/java/technologies/javase-downloads.html) (version 8 or higher)
- [Maven](https://maven.apache.org/download.cgi)

### Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/your-username/weather-api.git
    cd weather-api
    ```

2. Build the project:

    ```bash
    mvn clean install
    ```

3. Run the application:

    ```bash
    java -jar target/weather-api-1.0.0.jar
    ```

The application will start on `http://localhost:8080`.

## Usage

### Endpoints

- **Get Weather by City:**
  
  ```http
  GET /weather/city?city={city_name}
  ```

  Retrieves weather information for the specified city.

  Example:
  ```http
  GET /weather/city?city=Berlin
  ```

### Configuration

The application uses the [OpenWeatherMap API](https://openweathermap.org/api) to fetch weather data. You need to obtain an API key from OpenWeatherMap and configure it in the `application.properties` file.

```properties
# application.properties

# OpenWeatherMap API Key
openweathermap.api-key=your-api-key
```

Replace `your-api-key` with your actual OpenWeatherMap API key.

## Contributing

Contributions are welcome! If you find any issues or have improvements to suggest, feel free to open an issue or create a pull request.
