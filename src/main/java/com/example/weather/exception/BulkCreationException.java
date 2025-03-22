package com.example.weather.exception;

/**
 * Exception thrown when errors occur during bulk creation.
 */
public class BulkCreationException extends Exception {

  public BulkCreationException(String message) {
    super(message);
  }
}
