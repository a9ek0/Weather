package com.example.weather.exception;

/**
 * Exception indicating an error during JSON reading.
 */
public class JsonReadingException extends Exception {
  public JsonReadingException(String message) {
    super(message);
  }
}
