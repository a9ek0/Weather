package com.example.weather.exception;

/**
 * Custom exception class for handling cases where weather information is not found.
 */
public class WeatherNotFoundException extends Exception {
  public WeatherNotFoundException(String message) {
    super(message);
  }
}
