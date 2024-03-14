package com.example.weather.dto;

import com.example.weather.entity.Weather;
import com.example.weather.service.DateTimeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeatherUsersDtoTest {

  @Test
  void testToModel() {
    Weather weather = new Weather();
    weather.setCityName("TestCity");
    weather.setTemp(25.5);
    weather.setDescription("Sunny");
    weather.setRh(60.0);
    weather.setCountryCode("US");
    weather.setDateTime(DateTimeService.toDateTime("2022-03-04 12:00"));

    WeatherUsersDto weatherUsersDto = WeatherUsersDto.toModel(weather);

    assertEquals("TestCity", weatherUsersDto.getCity());
    assertEquals(25.5, weatherUsersDto.getTemp());
    assertEquals("Sunny", weatherUsersDto.getDescription());
    assertEquals(60.0, weatherUsersDto.getRh());
    assertEquals("US", weatherUsersDto.getCountryCode());
    assertEquals("2022-03-04 12:00", weatherUsersDto.getDateTime());
  }

  @Test
  void testGettersAndSetters() {
    WeatherUsersDto weatherUsersDto = new WeatherUsersDto();

    weatherUsersDto.setCity("TestCity");
    weatherUsersDto.setTemp(25.5);
    weatherUsersDto.setDescription("Sunny");
    weatherUsersDto.setRh(60.0);
    weatherUsersDto.setCountryCode("US");
    weatherUsersDto.setDateTime("2022-03-04T12:00:00");

    assertEquals("TestCity", weatherUsersDto.getCity());
    assertEquals(25.5, weatherUsersDto.getTemp());
    assertEquals("Sunny", weatherUsersDto.getDescription());
    assertEquals(60.0, weatherUsersDto.getRh());
    assertEquals("US", weatherUsersDto.getCountryCode());
    assertEquals("2022-03-04T12:00:00", weatherUsersDto.getDateTime());
  }
}
