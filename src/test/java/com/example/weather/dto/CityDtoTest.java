package com.example.weather.dto;

import com.example.weather.entity.City;
import com.example.weather.entity.Weather;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CityDtoTest {
  @Test
  void testGettersAndSetters() {
    CityDto cityDto = new CityDto();

    cityDto.setName("TestCity");
    cityDto.setWeatherBasicDtos(Arrays.asList(new WeatherBasicDto(), new WeatherBasicDto()));

    assertEquals("TestCity", cityDto.getName());
    assertEquals(2, cityDto.getWeatherBasicDtos().size());
  }
}
