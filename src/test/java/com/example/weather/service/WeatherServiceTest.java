package com.example.weather.service;

import com.example.weather.dto.WeatherDto;
import com.example.weather.entity.City;
import com.example.weather.entity.Weather;
import com.example.weather.exception.CityNotFoundException;
import com.example.weather.exception.IdNotFoundException;
import com.example.weather.exception.JsonReadingException;
import com.example.weather.exception.WeatherNotFoundException;
import com.example.weather.repository.CityRepo;
import com.example.weather.repository.WeatherRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

  @Mock
  private ObjectMapper objectMapper;

  @Mock
  private WeatherRepository weatherRepository;

  @Mock
  private CityRepo cityRepo;

  @InjectMocks
  private WeatherService weatherService;
  @Mock
  private RestTemplate restTemplate;

  @Test
  void testComplete_ValidData() throws WeatherNotFoundException {
    Long id = 1L;
    Weather updatedWeather = new Weather();
    updatedWeather.setCountryCode("US");
    updatedWeather.setTemp(25.0);
    updatedWeather.setRh(60.0);
    updatedWeather.setDateTime(LocalDateTime.now());
    updatedWeather.setDescription("Sunny");
    updatedWeather.setCityName("New York");

    Weather existingWeather = new Weather();
    existingWeather.setId(id);
    when(weatherRepository.findById(id)).thenReturn(java.util.Optional.of(existingWeather));
    when(weatherRepository.save(any(Weather.class))).thenAnswer(invocation -> invocation.getArgument(0));

    WeatherDto result = weatherService.complete(id, updatedWeather);

    assertNotNull(result);
    assertEquals(updatedWeather.getCountryCode(), result.getCountryCode());
    assertEquals(updatedWeather.getTemp(), result.getTemp());
    assertEquals(updatedWeather.getRh(), result.getRh());
    assertEquals(updatedWeather.getDescription(), result.getDescription());
    assertEquals(updatedWeather.getCityName(), result.getCityName());
  }

  @Test
  void testWeatherResponse() {
    Weather weather = new Weather();
    when(weatherRepository.save(weather)).thenReturn(weather);

    Weather result = weatherService.weatherResponse(weather);

    assertNotNull(result);
    assertEquals(weather, result);
  }

  @Test
  void testGetWeatherByCity_NonExistingCity() {
    String city = "NonExistingCity";
    when(weatherRepository.findByCityName(city)).thenReturn(new ArrayList<>());

    CityNotFoundException exception = assertThrows(CityNotFoundException.class,
            () -> weatherService.getWeather(city));
    assertEquals("City not found", exception.getMessage());
  }

  @Test
  void testProcessJson_ValidJson() throws JsonProcessingException {
    String jsonResponse = "{ \"temp\": 25.5, \"description\": \"Sunny\", \"city\": \"TestCity\" }";
    Weather expectedWeather = new Weather();
    when(objectMapper.readValue(jsonResponse, Weather.class)).thenReturn(expectedWeather);

    Weather result = weatherService.processJson(jsonResponse);

    assertNotNull(result);
    assertEquals(expectedWeather, result);
  }

  @Test
  void testProcessJson_InvalidJson() throws JsonProcessingException {
    String jsonResponse = "invalid json";
    when(objectMapper.readValue(jsonResponse, Weather.class)).thenThrow(JsonProcessingException.class);

    Weather result = weatherService.processJson(jsonResponse);

    assertNull(result);
  }

  @Test
  void testGetWeatherByCountryCode() {
    String countryCode = "US";
    List<Weather> weathers = new ArrayList<>();
    weathers.add(new Weather());
    when(weatherRepository.findByCountryCode(countryCode)).thenReturn(weathers);

    List<Weather> result = weatherService.getWeatherByCountryCode(countryCode);

    assertNotNull(result);
    assertFalse(result.isEmpty());
  }

  @Test
  void testCreateWeather_ValidData() throws WeatherNotFoundException, CityNotFoundException {
    Weather weather = new Weather();
    String cityName = "TestCity";
    City city = new City();
    when(cityRepo.findByName(cityName)).thenReturn(city);
    when(weatherRepository.save(weather)).thenReturn(weather);

    Weather result = weatherService.createWeather(weather, cityName);

    assertNotNull(result);
    assertEquals(city, result.getCity());
  }

  @Test
  void testCreateWeather_NullWeather() {
    assertThrows(WeatherNotFoundException.class, () -> weatherService.createWeather(null, "TestCity"));
  }

  @Test
  void testCreateWeather_NoSuchCity() {
    Weather weather = new Weather();
    String cityName = "NonExistingCity";
    when(cityRepo.findByName(cityName)).thenReturn(null);

    CityNotFoundException exception = assertThrows(CityNotFoundException.class,
            () -> weatherService.createWeather(weather, cityName));
    assertEquals("City not found", exception.getMessage());
  }

  @Test
  void testCreateWeatherBulk_ValidData() throws Exception {
    List<Weather> weatherList = new ArrayList<>();
    weatherList.add(new Weather());
    String cityName = "TestCity";
    City city = new City();
    when(cityRepo.findByName(cityName)).thenReturn(city);
    when(weatherRepository.save(any())).thenReturn(new Weather());

    assertDoesNotThrow(() -> weatherService.createWeatherBulk(weatherList, cityName));
  }

  @Test
  void testCreateWeatherBulk_NoWeatherProvided() {
    List<Weather> weatherList = new ArrayList<>();
    String cityName = "TestCity";

    WeatherNotFoundException exception = assertThrows(WeatherNotFoundException.class,
            () -> weatherService.createWeatherBulk(weatherList, cityName));
    assertEquals("weather not found", exception.getMessage());
  }

  @Test
  void testCreateWeatherBulk_ErrorCreatingWeather() throws Exception {
    List<Weather> weatherList = new ArrayList<>();
    weatherList.add(new Weather());
    String cityName = "TestCity";
    City city = new City();
    when(cityRepo.findByName(cityName)).thenReturn(city);
    when(weatherRepository.save(any())).thenThrow(new RuntimeException("Error"));

    Exception exception = assertThrows(Exception.class,
            () -> weatherService.createWeatherBulk(weatherList, cityName));
    assertTrue(exception.getMessage().contains("Errors occurred during bulk creation"));
  }

  @Test
  void testDelete_ValidId() throws IdNotFoundException {
    long id = 1L;
    when(weatherRepository.findById(id)).thenReturn(Optional.empty());

    IdNotFoundException exception = assertThrows(IdNotFoundException.class,
            () -> weatherService.delete(id));
    assertEquals("weather with such id not found", exception.getMessage());

    verify(weatherRepository, never()).deleteById(id);
  }

  @Test
  void testDelete_InvalidId() throws IdNotFoundException {
    long id = 1L;
    when(weatherRepository.findById(id)).thenReturn(Optional.of(new Weather()));

    weatherService.delete(id);

    verify(weatherRepository, times(1)).deleteById(id);
  }
}
