package com.example.weather.exception;

/**
 * Exception indicating that an ID was not found.
 */
public class IdNotFoundException extends Exception {
  public IdNotFoundException(String message) {
    super(message);
  }

}
