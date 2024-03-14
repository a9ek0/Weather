package com.example.weather.dto;

import com.example.weather.entity.Weather;
import com.example.weather.service.DateTimeService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeatherBasicDtoTest {

  @Test
  void testToModel() {
    Weather weather = new Weather();
    weather.setTemp(25.5);
    weather.setDescription("Sunny");
    weather.setRh(60.0);
    weather.setCountryCode("US");
    weather.setDateTime(DateTimeService.toDateTime("2022-03-04 12:00"));

    WeatherBasicDto weatherBasicDto = WeatherBasicDto.toModel(weather);

    assertEquals(25.5, weatherBasicDto.getTemp());
    assertEquals("Sunny", weatherBasicDto.getDescription());
    assertEquals(60.0, weatherBasicDto.getRh());
    assertEquals("US", weatherBasicDto.getCountryCode());
    assertEquals("2022-03-04 12:00", weatherBasicDto.getDateTime());
  }

  @Test
  void testGettersAndSetters() {
    WeatherBasicDto weatherBasicDto = new WeatherBasicDto();

    weatherBasicDto.setTemp(25.5);
    weatherBasicDto.setDescription("Sunny");
    weatherBasicDto.setRh(60.0);
    weatherBasicDto.setCountryCode("US");
    weatherBasicDto.setDateTime("2022-03-04 12:00");

    assertEquals(25.5, weatherBasicDto.getTemp());
    assertEquals("Sunny", weatherBasicDto.getDescription());
    assertEquals(60.0, weatherBasicDto.getRh());
    assertEquals("US", weatherBasicDto.getCountryCode());
    assertEquals("2022-03-04 12:00", weatherBasicDto.getDateTime());
  }
}
