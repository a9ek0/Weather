package com.example.weather.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeatherExceptionHandlerTest {

  private final WeatherExceptionHandler exceptionHandler = new WeatherExceptionHandler();

  @Test
  void handleRuntimeException() {
    RuntimeException exception = new RuntimeException("Test Runtime Exception");
    ResponseEntity<Object> responseEntity = exceptionHandler.handleRuntimeException(exception, null);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    assertEquals("500: Internal Server Error", responseEntity.getBody());
  }

  @Test
  void handleHttpClientErrorException() {
    HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Test Bad Request");
    ResponseEntity<Object> responseEntity = exceptionHandler.handleHttpClientErrorException(exception, null);

    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    assertEquals("400: Bad Request", responseEntity.getBody());
  }

  @Test
  void handleNoResourceFoundException() {
    NoResourceFoundException exception = new NoResourceFoundException(HttpMethod.GET, "Test Not Found");
    ResponseEntity<Object> responseEntity = exceptionHandler.handleNoResourceFoundException(exception, null);

    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertEquals("404: Not Found", responseEntity.getBody());
  }
}
