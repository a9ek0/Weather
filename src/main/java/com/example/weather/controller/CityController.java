package com.example.weather.controller;

import com.example.weather.dto.CityDto;
import com.example.weather.entity.City;
import com.example.weather.entity.Weather;
import com.example.weather.exception.CityNotFoundException;
import com.example.weather.exception.IdNotFoundException;
import com.example.weather.exception.WeatherExceptionHandler;
import com.example.weather.service.CityService;
import com.example.weather.service.RequestCounterService;
import com.example.weather.service.WeatherService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Controller class for managing City-related API endpoints.
 */
@RestController
@RequestMapping("/weather/city")
public class CityController {
  private static final Logger log = LoggerFactory.getLogger(CityController.class);
  final WeatherService weatherService;
  final CityService cityService;
  final WeatherExceptionHandler weatherExceptionHandler;
  final RequestCounterService requestCounterService;

  /**
   * Constructor for CityController.
   *
   * @param cityService              The service for managing City entities.
   * @param weatherService           The service for managing Weather entities.
   * @param weatherExceptionHandler The exception handler for weather-related exceptions.
   */
  public CityController(CityService cityService,
                        WeatherService weatherService,
                        WeatherExceptionHandler weatherExceptionHandler,
                        RequestCounterService requestCounterService) {
    this.cityService = cityService;
    this.weatherService = weatherService;
    this.weatherExceptionHandler = weatherExceptionHandler;
    this.requestCounterService = requestCounterService;
  }

  /**
   * REST endpoint to create a new city with weather information.
   *
   * @param city The City object containing the information to be saved.
   * @return ResponseEntity with a success message if the city was saved successfully.
   */
  @PostMapping
  @CrossOrigin
  public ResponseEntity<String> cityResponse(@RequestBody City city) {
    log.info("post endpoint /city was called");
    requestCounterService.increment();
    try {
      if (cityService.findCityByCityName(city.getName()) != null) {
        return ResponseEntity.badRequest().body("City already exists");
      }

      List<Weather> weathers = weatherService.findWeather(city.getName());
      for (Weather weather : weathers) {
        city.getWeatherList().add(weather);
        weather.setCity(city);
      }

      cityService.cityResponse(city);
      log.info("city was saved successfully");
      return ResponseEntity.ok("City was saved successfully");
    } catch (Exception e) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  /**
   * Retrieves information about a city based on its ID.
   *
   * @param id The ID of the city to retrieve.
   * @return ResponseEntity with the retrieved city information or an error response.
   */
  @GetMapping("/id/{id}")
  @CrossOrigin
  public ResponseEntity<CityDto> getCity(@PathVariable Long id) {
    log.info("get endpoint /id/{id} was called");
    requestCounterService.increment();
    try {
      return ResponseEntity.ok(cityService.getCity(id));
    } catch (CityNotFoundException e) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    } catch (Exception e) {
      throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  /**
   * Deletes a city based on its ID.
   *
   * @param id The ID of the city to delete.
   * @return ResponseEntity with a success message or an error response.
   */
  @DeleteMapping("/delete/{id}")
  @CrossOrigin
  public ResponseEntity<String> deleteCity(@PathVariable Long id) {
    log.info("delete endpoint /del/{id} was called");
    requestCounterService.increment();
    try {
      log.info("deleting city with ID {}", id);
      cityService.delete(id);
      return ResponseEntity.ok("Deleted successfully");
    } catch (IdNotFoundException e) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    } catch (Exception e) {
      throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
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
  @CrossOrigin
  public ResponseEntity<String> updateCity(@PathVariable Long id, @RequestBody City city) {
    log.info("update endpoint /update/{id} was called");
    requestCounterService.increment();
    try {
      if (cityService.findCityByCityName(city.getName()) != null) {
        return ResponseEntity.badRequest().body("City already exists");
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
      log.info("city with ID {} updated successfully", id);
      return ResponseEntity.ok("Updated successfully");
    } catch (Exception e) {
      throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }
}
