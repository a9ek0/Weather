package com.example.weather.service;

import com.example.weather.dto.CityDto;
import com.example.weather.entity.City;
import com.example.weather.exception.CityNotFoundException;
import com.example.weather.exception.IdNotFoundException;
import com.example.weather.repository.CityRepository;
import com.example.weather.repository.WeatherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

  @Mock
  private CityRepository cityRepository;

  @Mock
  private WeatherRepository weatherRepository;

  @InjectMocks
  private CityService cityService;

  @Test
  void testCityResponse() {
    City city = new City();
    when(cityRepository.save(city)).thenReturn(city);

    City result = cityService.cityResponse(city);

    assertNotNull(result);
    assertEquals(city, result);
  }

  @Test
  void testGetCityById_ExistingId() throws CityNotFoundException {
    long id = 1L;
    City city = new City();
    when(cityRepository.findById(id)).thenReturn(Optional.of(city));

    CityDto result = cityService.getCity(id);

    assertNotNull(result);
  }

  @Test
  void testFindCityById_ValidId() throws CityNotFoundException {
    long id = 1L;
    City expectedCity = new City();
    when(cityRepository.findById(id)).thenReturn(Optional.of(expectedCity));

    City result = cityService.findCityById(id);

    assertNotNull(result);
    assertEquals(expectedCity, result);
  }

  @Test
  void testFindCityById_NullId() throws CityNotFoundException {
    assertNull(cityService.findCityById(null));
  }

  @Test
  void testFindCityByCityName_ValidName() {
    String name = "TestCity";
    City expectedCity = new City();
    when(cityRepository.findByName(name)).thenReturn(expectedCity);

    City result = cityService.findCityByCityName(name);

    assertNotNull(result);
    assertEquals(expectedCity, result);
  }

  @Test
  void testFindCityByCityName_NullName() {
    assertNull(cityService.findCityByCityName(null));
  }

  @Test
  void testDelete_ValidId() throws IdNotFoundException {
    long id = 1L;
    when(cityRepository.findById(id)).thenReturn(Optional.empty());

    IdNotFoundException exception = assertThrows(IdNotFoundException.class,
            () -> cityService.delete(id));
    assertEquals("city with such id not found", exception.getMessage());

    verify(cityRepository, never()).deleteById(id);
  }

  @Test
  void testDelete_InvalidId() throws IdNotFoundException {
    long id = 1L;
    when(cityRepository.findById(id)).thenReturn(Optional.of(new City()));

    cityService.delete(id);

    verify(cityRepository, times(1)).deleteById(id);
  }

  @Test
  void testComplete_ValidId() throws CityNotFoundException {
    long id = 1L;
    City existingCity = new City();
    existingCity.setId(id);
    City updatedCity = new City();
    updatedCity.setId(id);
    when(cityRepository.findById(id)).thenReturn(Optional.of(existingCity));
    when(cityRepository.save(any())).thenReturn(updatedCity);

    CityDto result = cityService.complete(id, updatedCity);

    assertNotNull(result);
  }
}
