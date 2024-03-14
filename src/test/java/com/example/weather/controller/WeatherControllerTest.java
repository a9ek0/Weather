package com.example.weather.controller;

import com.example.weather.dto.WeatherDto;
import com.example.weather.entity.Weather;
import com.example.weather.exception.CityNotFoundException;
import com.example.weather.exception.IdNotFoundException;
import com.example.weather.service.RequestCounterService;
import com.example.weather.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherControllerTest {

  @Mock
  private WeatherService weatherService;
  @Mock
  private RequestCounterService requestCounterService;
  @InjectMocks
  private WeatherController weatherController;

  @Test
  void testWeatherResponse_Success() {
    Weather weather = new Weather();
    ResponseEntity<String> response = weatherController.weatherResponse(weather);
    assertEquals("Weather was saved successfully", response.getBody());
  }

  @Test
  void testWeatherResponse_Failure() {
    Weather weather = new Weather();
    doThrow(new RuntimeException("Test Exception")).when(weatherService).weatherResponse(weather);

    HttpClientErrorException exception = org.junit.jupiter.api.Assertions.assertThrows(
            HttpClientErrorException.class,
            () -> weatherController.weatherResponse(weather)
    );
    assertEquals("400 Test Exception", exception.getMessage());
  }

  @Test
  void testCreateWeathersBulk_Success() {
    List<Weather> weathers = new ArrayList<>();
    String city = "TestCity";

    ResponseEntity<String> response = weatherController.createWeathersBulk(weathers, city);

    assertEquals("Weathers was saved successfully", response.getBody());
  }

  @Test
  void testCreateWeathersBulk_Failure() throws Exception {
    List<Weather> weathers = new ArrayList<>();
    String city = "TestCity";
    doThrow(new RuntimeException("Test Exception")).when(weatherService).createWeatherBulk(weathers, city);

    HttpClientErrorException exception = org.junit.jupiter.api.Assertions.assertThrows(
            HttpClientErrorException.class,
            () -> weatherController.createWeathersBulk(weathers, city)
    );
    assertEquals("400 Test Exception", exception.getMessage());
  }


  @Test
  void testDeleteWeather_Success() throws IdNotFoundException {
    Long id = 1L;

    ResponseEntity<String> response = weatherController.deleteWeather(id);

    assertEquals("Deleted successfully", response.getBody());
    verify(weatherService, times(1)).delete(id);
  }

  @Test
  void testDeleteWeather_IdNotFoundException() throws IdNotFoundException {
    Long id = 1L;
    doThrow(new IdNotFoundException("ID not found")).when(weatherService).delete(id);

    HttpClientErrorException exception = org.junit.jupiter.api.Assertions.assertThrows(
            HttpClientErrorException.class,
            () -> weatherController.deleteWeather(id)
    );
    assertEquals("400 ID not found", exception.getMessage());
  }

  @Test
  void testDeleteWeather_InternalServerError() throws IdNotFoundException {
    Long id = 1L;
    doThrow(new RuntimeException("Test Exception")).when(weatherService).delete(id);

    HttpClientErrorException exception = org.junit.jupiter.api.Assertions.assertThrows(
            HttpClientErrorException.class,
            () -> weatherController.deleteWeather(id)
    );
    assertEquals("500 Test Exception", exception.getMessage());
  }

  @Test
  void testUpdateWeather_Success() {
    Long id = 1L;
    Weather updatedWeather = new Weather();

    ResponseEntity<String> response = weatherController.updateWeather(id, updatedWeather);

    assertEquals("Updated successfully", response.getBody());
    verify(weatherService, times(1)).complete(id, updatedWeather);
  }

  @Test
  void testUpdateWeather_InternalServerError() {
    Long id = 1L;
    Weather updatedWeather = new Weather();
    doThrow(new RuntimeException("Test Exception")).when(weatherService).complete(id, updatedWeather);

    HttpClientErrorException exception = org.junit.jupiter.api.Assertions.assertThrows(
            HttpClientErrorException.class,
            () -> weatherController.updateWeather(id, updatedWeather)
    );
    assertEquals("500 Test Exception", exception.getMessage());
  }
}

