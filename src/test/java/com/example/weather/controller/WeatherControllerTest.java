package com.example.weather.controller;

import com.example.weather.component.Cache;
import com.example.weather.dto.WeatherDto;
import com.example.weather.entity.Weather;
import com.example.weather.exception.CityNotFoundException;
import com.example.weather.exception.WeatherExceptionHandler;
import com.example.weather.service.CityService;
import com.example.weather.service.UserService;
import com.example.weather.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherControllerTest {

  @Mock
  private WeatherService weatherService;

  @Mock
  private CityService cityService;

  @Mock
  private UserService userService;

  @Mock
  private Cache<String, List<WeatherDto>> cache;

  @Mock
  private WeatherExceptionHandler weatherExceptionHandler;

  @InjectMocks
  private WeatherController weatherController;

  @Test
  void testWeatherResponse_Success() {
    Weather weather = new Weather();
    when(weatherService.weatherResponse(weather)).thenReturn(weather);

    ResponseEntity<String> response = weatherController.weatherResponse(weather);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Weather was saved successfully!", response.getBody());
  }
}
