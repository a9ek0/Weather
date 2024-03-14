package com.example.weather.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonReadingExceptionTest {

  @Test
  void testConstructor() {
    String message = "Test Message";
    JsonReadingException exception = new JsonReadingException(message);

    assertEquals(message, exception.getMessage());
  }
}
