package com.example.weather.exception;

/**
 * Custom exception class representing a bad request scenario.
 * This exception is thrown when the client's request is malformed or invalid.
 */
public class BadRequestException extends RuntimeException {

  public BadRequestException(String message) {
    super(message);
  }
}

