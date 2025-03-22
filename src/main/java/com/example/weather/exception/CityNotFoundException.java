package com.example.weather.exception;

/**
 * Exception indicating that a city was not found.
 * This exception is thrown when attempting to access or manipulate a city that does not exist.
 */
public class CityNotFoundException extends Exception {

  public CityNotFoundException(String message) {
    super(message);
  }

}
