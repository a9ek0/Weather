package com.example.weather.controller;

import com.example.weather.dto.CityDto;
import com.example.weather.entity.City;
import com.example.weather.exception.CityNotFoundException;
import com.example.weather.exception.IdNotFoundException;
import com.example.weather.exception.UserNotFoundException;
import com.example.weather.service.CityService;
import com.example.weather.service.RequestCounterService;
import com.example.weather.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CityControllerTest {

  @Mock
  private CityService cityService;
  @Mock
  private WeatherService weatherService;
  @Mock
  private RequestCounterService requestCounterService;
  @InjectMocks
  private CityController cityController;
  private static final Logger log = LoggerFactory.getLogger(CityController.class);
  @Test
  void testCityResponse_Success() {
    City newCity = new City();

    when(cityService.findCityByCityName(newCity.getName())).thenReturn(null);
    when(weatherService.findWeather(newCity.getName())).thenReturn(new ArrayList<>());

    ResponseEntity responseEntity = cityController.cityResponse(newCity);

    assertEquals(ResponseEntity.ok("City was saved successfully"), responseEntity);
    verify(cityService, times(1)).cityResponse(newCity);
  }

  @Test
  void testCityResponse_ExceptionThrown() {
    City newCity = new City();

    when(cityService.findCityByCityName(newCity.getName())).thenReturn(null);
    when(weatherService.findWeather(newCity.getName())).thenThrow(new RuntimeException("error"));

    HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
      cityController.cityResponse(newCity);
    });

    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    assertEquals("400 error", exception.getMessage());
    verify(cityService, never()).cityResponse(newCity);
  }

  @Test
  void testGetCity_Success() throws CityNotFoundException {
    Long cityId = 1L;
    City city = new City();

    CityDto cityDto = CityDto.toModel(city);
    when(cityService.getCity(cityId)).thenReturn(cityDto);

    ResponseEntity responseEntity = cityController.getCity(cityId);

    assertEquals(ResponseEntity.ok(cityDto), responseEntity);
    verify(cityService, times(1)).getCity(cityId);
  }

  @Test
  void testGetCity_UserNotFoundException() throws CityNotFoundException {
    Long cityId = 1L;

    doThrow(new CityNotFoundException("city not found")).when(cityService).getCity(cityId);

    HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
      cityController.getCity(cityId);
    });

    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    assertEquals("400 city not found", exception.getMessage());

    verify(cityService, times(1)).getCity(cityId);
  }


  @Test
  void testGetCity_ExceptionThrown() throws CityNotFoundException {
    Long cityId = 1L;

    when(cityService.getCity(cityId)).thenThrow(new RuntimeException("error"));

    HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
      cityController.getCity(cityId);
    });

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
    assertEquals("500 error", exception.getMessage());
    verify(cityService, times(1)).getCity(cityId);
  }

  @Test
  void testDeleteCity_Success() throws IdNotFoundException {
    Long cityId = 1L;

    ResponseEntity responseEntity = cityController.deleteCity(cityId);

    assertEquals(ResponseEntity.ok("Deleted successfully"), responseEntity);
    verify(cityService, times(1)).delete(cityId);
  }

  @Test
  void testDeleteCity_IdNotFoundException() throws IdNotFoundException {
    Long cityId = 1L;

    doThrow(new IdNotFoundException("city not found")).when(cityService).delete(cityId);

    HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
      cityController.deleteCity(cityId);
    });

    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    assertEquals("400 city not found", exception.getMessage());
    verify(cityService, times(1)).delete(cityId);
  }

  @Test
  void testDeleteCity_ExceptionThrown() throws IdNotFoundException {
    Long cityId = 1L;

    doThrow(new RuntimeException("error")).when(cityService).delete(cityId);

    HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
      cityController.deleteCity(cityId);
    });

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
    assertEquals("500 error", exception.getMessage());
    verify(cityService, times(1)).delete(cityId);
  }

  @Test
  void testUpdateCity_Success() throws CityNotFoundException {
    Long id = 1L;
    City city = new City();

    when(cityService.findCityByCityName(city.getName())).thenReturn(null);
    when(cityService.findCityById(id)).thenReturn(new City());
    when(weatherService.findWeather(any())).thenReturn(new ArrayList<>());

    ResponseEntity<String> response = cityController.updateCity(id, city);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Updated successfully", response.getBody());

    verify(cityService, times(1)).complete(id, city);
  }

  @Test
  void testUpdateCity_CityAlreadyExists() throws CityNotFoundException {
    Long id = 1L;
    City city = new City();
    city.setName("ExistingCity");

    when(cityService.findCityByCityName("ExistingCity")).thenReturn(new City());

    ResponseEntity<String> response = cityController.updateCity(id, city);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("City already exists", response.getBody());

    verify(cityService, never()).complete(id, city);
  }

  @Test
  void testUpdateCity_InternalServerError() throws CityNotFoundException {
    Long id = 1L;
    City city = new City();

    when(cityService.findCityByCityName(city.getName())).thenReturn(null);
    when(cityService.findCityById(id)).thenThrow(new RuntimeException("Internal Server Error"));

    assertThrows(HttpClientErrorException.class, () -> {
      cityController.updateCity(id, city);
    });
  }
}

