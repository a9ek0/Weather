package com.example.weather.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BadRequestExceptionTest {

  @Test
  void testConstructor() {
    String message = "Test Message";
    BadRequestException exception = new BadRequestException(message);

    assertEquals(message, exception.getMessage());
  }
}
