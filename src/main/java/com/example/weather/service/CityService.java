package com.example.weather.service;

import com.example.weather.controller.CityController;
import com.example.weather.dto.CityDto;
import com.example.weather.entity.City;
import com.example.weather.exception.CityNotFoundException;
import com.example.weather.exception.IdNotFoundException;
import com.example.weather.repository.CityRepository;
import com.example.weather.repository.WeatherRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service class for managing City entities.
 * Provides methods for handling CRUD operations on City entities.
 */
@Service
public class CityService {

  final CityRepository cityRepository;
  static final String CITY_NOT_FOUND = "City not found";
  final WeatherRepository weatherRepository;

  public CityService(CityRepository cityRepository, WeatherRepository weatherRepository) {
    this.cityRepository = cityRepository;
    this.weatherRepository = weatherRepository;
  }

  /**
   * Saves a City entity to the repository.
   *
   * @param city The City entity to be saved.
   * @return The saved City entity.
   */
  public City cityResponse(City city) {
    return cityRepository.save(city);
  }

  /**
   * Retrieves a CityDto by its ID.
   *
   * @param id The ID of the City entity.
   * @return The CityDto corresponding to the ID.
   * @throws CityNotFoundException If the City entity is not found.
   */
  public CityDto getCity(Long id) throws CityNotFoundException {
    Optional<City> optionalCity = cityRepository.findById(id);
    City city = optionalCity.orElseThrow(() -> new CityNotFoundException(CITY_NOT_FOUND));
    if (city == null) {
      throw new CityNotFoundException("city not found");
    }
    Logger log = LoggerFactory.getLogger(CityController.class);
    log.info("city with ID {} retrieved successfully", id);
    return CityDto.toModel(city);
  }

  /**
   * Finds a City entity by its ID.
   *
   * @param cityId The ID of the City entity.
   * @return The City entity if found, or null if not found.
   */
  public City findCityById(Long cityId) throws CityNotFoundException {
    if (cityId != null) {
      Optional<City> optionalCity = cityRepository.findById(cityId);
      return optionalCity.orElseThrow(() -> new CityNotFoundException(CITY_NOT_FOUND));
    }
    return null;
  }

  /**
   * Finds a City entity by its name.
   *
   * @param name The name of the city to find.
   * @return The City entity if found, or null if not found.
   */
  public City findCityByCityName(String name) {
    if (name != null) {
      return cityRepository.findByName(name);
    }
    return null;
  }

  /**
   * Deletes a City entity by its ID.
   *
   * @param id The ID of the City entity to delete.
   * @return The ID of the deleted City entity.
   * @throws IdNotFoundException If the City entity with the given ID is not found.
   */
  public Long delete(Long id) throws IdNotFoundException {
    if (cityRepository.findById(id).isEmpty()) {
      throw new IdNotFoundException("city with such id not found");
    }
    cityRepository.deleteById(id);
    Logger log = LoggerFactory.getLogger(CityController.class);
    log.info("city with ID {} deleted successfully", id);
    return id;
  }

  /**
   * Updates a City entity with new information.
   *
   * @param id           The ID of the City entity to update.
   * @param updatedCity  The updated City entity.
   * @return The updated CityDto.
   */
  public CityDto complete(Long id, City updatedCity) throws CityNotFoundException {
    Optional<City> optionalCity = cityRepository.findById(id);
    City city = optionalCity.orElseThrow(() -> new CityNotFoundException(CITY_NOT_FOUND));

    city.setName(updatedCity.getName());
    if (!updatedCity.getWeatherList().isEmpty()) {
      city.setWeatherList(updatedCity.getWeatherList());
    }
    return CityDto.toModel(cityRepository.save(city));
  }
}
