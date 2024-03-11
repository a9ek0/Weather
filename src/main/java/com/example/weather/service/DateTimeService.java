package com.example.weather.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

/**
 * Service class for managing date and time operations.
 * Provides methods for converting between LocalDateTime and String representations.
 */
@Service
public class DateTimeService {

  /**
   * Private constructor to hide the implicit public one.
   */
  private DateTimeService() {
    // Private constructor to hide the implicit public one
  }

  /**
   * Converts a String representation of a date and time to LocalDateTime.
   *
   * @param dateString The String representation of a date and time.
   * @return The LocalDateTime representation of the input String.
   */
  public static LocalDateTime toDateTime(String dateString) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    return LocalDateTime.parse(dateString, formatter);
  }

  /**
   * Converts a LocalDateTime object to a String representation.
   *
   * @param dateTime The LocalDateTime object to convert.
   * @return The String representation of the input LocalDateTime.
   */
  public static String toString(LocalDateTime dateTime) {
    String date;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    date = dateTime.format(formatter);

    return date;
  }
}
