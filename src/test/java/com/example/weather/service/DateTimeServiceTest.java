package com.example.weather.service;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateTimeServiceTest {

  @Test
  void testToDateTime() {
    String dateString = "2024-03-04 15:30";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    LocalDateTime expectedDateTime = LocalDateTime.parse(dateString, formatter);

    LocalDateTime result = DateTimeService.toDateTime(dateString);

    assertEquals(expectedDateTime, result);
  }

  @Test
  void testToString() {
    LocalDateTime dateTime = LocalDateTime.of(2024, 3, 4, 15, 30);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    String expectedString = dateTime.format(formatter);

    String result = DateTimeService.toString(dateTime);

    assertEquals(expectedString, result);
  }
}
