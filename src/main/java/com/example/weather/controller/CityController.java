package com.example.weather.controller;

import com.example.weather.entity.City;
import com.example.weather.entity.Weather;
import com.example.weather.exception.CityAlreadyExistsException;
import com.example.weather.exception.IdNotFoundException;
import com.example.weather.exception.UserNotFoundException;
import com.example.weather.exception.WeatherExceptionHandler;
import com.example.weather.service.CityService;
import com.example.weather.service.WeatherService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for managing City-related API endpoints.
 */
@RestController
@RequestMapping("/weather/city")
public class CityController {
  private static final Logger log = LoggerFactory.getLogger(CityController.class);
  private static final String ERROR_MESSAGE = "Error occurred!";
  final WeatherService weatherService;
  final CityService cityService;
  final WeatherExceptionHandler weatherExceptionHandler;

  /**
   * Constructor for CityController.
   *
   * @param cityService              The service for managing City entities.
   * @param weatherService           The service for managing Weather entities.
   * @param weatherExceptionHandler The exception handler for weather-related exceptions.
   */
  public CityController(CityService cityService,
                        WeatherService weatherService,
                        WeatherExceptionHandler weatherExceptionHandler) {
    this.cityService = cityService;
    this.weatherService = weatherService;
    this.weatherExceptionHandler = weatherExceptionHandler;
  }

  /**
   * REST endpoint to create a new city with weather information.
   *
   * @param city The City object containing the information to be saved.
   * @return ResponseEntity with a success message if the city was saved successfully.
   */
  @PostMapping
  public ResponseEntity<String> cityResponse(@RequestBody City city) {
    log.info("Data creation processing.");
    try {
      if (cityService.findCityByCityName(city.getName()) != null) {
        throw new CityAlreadyExistsException("City already exists!");
      }

      List<Weather> weathers = weatherService.findWeather(city.getName());
      for (Weather weather : weathers) {
        city.getWeatherList().add(weather);
        weather.setCity(city);
      }

      cityService.cityResponse(city);
      log.info("City {} was saved successfully.", city.getName());
      return ResponseEntity.ok("City was saved successfully!");
    } catch (Exception e) {
      log.error("Error saving city.");
      return weatherExceptionHandler.handleInternalServerError(e);
    }
  }

  /**
   * Retrieves information about a city based on its ID.
   *
   * @param id The ID of the city to retrieve.
   * @return ResponseEntity with the retrieved city information or an error response.
   */
  @GetMapping("/id/{id}")
  public ResponseEntity getUser(@PathVariable Long id) {
    log.info("Processing request for data.");
    try {
      log.info("City with ID {} saved successfully.", id);
      return ResponseEntity.ok(cityService.getCity(id));
    } catch (UserNotFoundException e) {
      log.warn("City with ID {} not found. {}", id, e.getMessage());
      return weatherExceptionHandler.handleBadRequest(e);
    } catch (Exception e) {
      log.error("Error processing request for city with ID {}.", id);
      return weatherExceptionHandler.handleInternalServerError(e);
    }
  }

  /**
   * Deletes a city based on its ID.
   *
   * @param id The ID of the city to delete.
   * @return ResponseEntity with a success message or an error response.
   */
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<String> deleteWeather(@PathVariable Long id) {
    log.info("Data deletion processing.");
    try {
      log.info("Deleting city with ID {}.", id);
      cityService.delete(id);
      log.info("City with ID {} deleted successfully.", id);
      return ResponseEntity.ok("Deleted successfully!");
    } catch (IdNotFoundException e) {
      log.warn("Error deleting city with ID {}. {}", id, e.getMessage());
      return weatherExceptionHandler.handleBadRequest(e);
    } catch (Exception e) {
      log.error("Error deleting city with ID {}.", id, e);
      return weatherExceptionHandler.handleInternalServerError(e);
    }
  }

  /**
   * Updates information about a city based on its ID.
   *
   * @param id   The ID of the city to update.
   * @param city The updated city information.
   * @return ResponseEntity with a success message or an error response.
   */
  @PutMapping("/update/{id}")
  public ResponseEntity<String> updateWeather(@PathVariable Long id, @RequestBody City city) {
    log.info("Data update processing.");
    try {
      if (cityService.findCityByCityName(city.getName()) != null) {
        throw new CityAlreadyExistsException("City already exists!");
      }

      List<Weather> weathers = weatherService.findWeather(cityService.findCityById(id).getName());

      for (Weather weather : weathers) {
        weather.setCity(null);
      }
      city.setWeatherList(new ArrayList<>());

      weathers = weatherService.findWeather(city.getName());
      for (Weather weather : weathers) {
        city.getWeatherList().add(weather);
        weather.setCity(city);
      }

      city.setId(id);

      cityService.complete(id, city);
      log.info("City with ID {} updated successfully.", id);
      return ResponseEntity.ok("Updated successfully!");
    } catch (CityAlreadyExistsException e) {
      log.warn("Error updating city with ID {}. {}", id, e.getMessage());
      return weatherExceptionHandler.handleBadRequest(e);
    } catch (Exception e) {
      log.error("Error updating city with ID {}.", id, e);
      return weatherExceptionHandler.handleInternalServerError(e);
    }
  }
}
