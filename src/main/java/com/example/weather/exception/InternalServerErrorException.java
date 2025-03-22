package com.example.weather.exception;

/**
 * RuntimeException indicating an internal server error.
 */
public class InternalServerErrorException extends RuntimeException {

  public InternalServerErrorException(String message) {
    super(message);
  }
}

