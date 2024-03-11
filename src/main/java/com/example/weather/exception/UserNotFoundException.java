package com.example.weather.exception;

/**
 * Exception class indicating that a user was not found.
 */
public class UserNotFoundException extends Exception {
  public UserNotFoundException(String message) {
    super(message);
  }
}
