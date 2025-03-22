package com.example.weather.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InternalServerErrorExceptionTest {

  @Test
  void testConstructor() {
    String message = "Test Message";
    InternalServerErrorException exception = new InternalServerErrorException(message);

    assertEquals(message, exception.getMessage());
  }
}
