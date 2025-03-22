package com.example.weather.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CityAlreadyExistsExceptionTest {

  @Test
  void testConstructor() {
    String message = "Test Message";
    CityAlreadyExistsException exception = new CityAlreadyExistsException(message);

    assertEquals(message, exception.getMessage());
  }
}
