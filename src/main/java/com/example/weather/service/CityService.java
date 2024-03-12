package com.example.weather.service;

import com.example.weather.dto.CityDto;
import com.example.weather.entity.City;
import com.example.weather.exception.IdNotFoundException;
import com.example.weather.exception.UserNotFoundException;
import com.example.weather.repository.CityRepo;
import com.example.weather.repository.WeatherRepository;
import org.springframework.stereotype.Service;

/**
 * Service class for managing City entities.
 * Provides methods for handling CRUD operations on City entities.
 */
@Service
public class CityService {

  final CityRepo cityRepo;

  final WeatherRepository weatherRepository;

  public CityService(CityRepo cityRepo, WeatherRepository weatherRepository) {
    this.cityRepo = cityRepo;
    this.weatherRepository = weatherRepository;
  }

  /**
   * Saves a City entity to the repository.
   *
   * @param city The City entity to be saved.
   * @return The saved City entity.
   */
  public City cityResponse(City city) {
    return cityRepo.save(city);
  }

  /**
   * Retrieves a CityDto by its ID.
   *
   * @param id The ID of the City entity.
   * @return The CityDto corresponding to the ID.
   * @throws UserNotFoundException If the City entity is not found.
   */
  public CityDto getCity(Long id) throws UserNotFoundException {
    City city = cityRepo.findById(id).get();
    if (city == null) {
      throw new UserNotFoundException("user not found");
    }
    return CityDto.toModel(city);
  }

  /**
   * Finds a City entity by its ID.
   *
   * @param userId The ID of the City entity.
   * @return The City entity if found, or null if not found.
   */
  public City findCityById(Long userId) {
    if (userId != null) {
      return cityRepo.findById(userId).get();
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
      return cityRepo.findByName(name);
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
    if (cityRepo.findById(id).isEmpty()) {
      throw new IdNotFoundException("city with such id not found");
    }
    cityRepo.deleteById(id);
    return id;
  }

  /**
   * Updates a City entity with new information.
   *
   * @param id           The ID of the City entity to update.
   * @param updatedCity  The updated City entity.
   * @return The updated CityDto.
   */
  public CityDto complete(Long id, City updatedCity) {
    City city = cityRepo.findById(id).get();

    city.setName(updatedCity.getName());
    if (!updatedCity.getWeatherList().isEmpty()) {
      city.setWeatherList(updatedCity.getWeatherList());
    }
    return CityDto.toModel(cityRepo.save(city));
  }
}
