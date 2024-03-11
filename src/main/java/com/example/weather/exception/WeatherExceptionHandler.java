package com.example.weather.exception;

import com.example.weather.controller.WeatherController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Global exception handler for the Weather application.
 */
@ControllerAdvice
public class WeatherExceptionHandler {
  private static final Logger log = LoggerFactory.getLogger(WeatherExceptionHandler.class);

  /**
   * Handles exceptions of type InternalServerErrorException.
   *
   * @param e The InternalServerErrorException instance.
   * @return ResponseEntity with a status of INTERNAL_SERVER_ERROR and the exception message.
   */
  @ExceptionHandler(InternalServerErrorException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<String> handleInternalServerError(Exception e) {

    log.error("Internal Server Error");
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
  }

  /**
   * Handles exceptions of type BadRequestException.
   *
   * @param e The BadRequestException instance.
   * @return ResponseEntity with a status of BAD_REQUEST and the exception message.
   */
  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<String> handleBadRequest(Exception e) {
    log.warn("Bad Request");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
  }
}

