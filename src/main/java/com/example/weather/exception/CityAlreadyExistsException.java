package com.example.weather.exception;

/**
 * Exception thrown when attempting to create a city that already exists.
 */
public class CityAlreadyExistsException extends Exception {

  public CityAlreadyExistsException(String message) {
    super(message);
  }

}
