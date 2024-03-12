package com.example.weather.controller;

import com.example.weather.component.Cache;
import com.example.weather.dto.WeatherDto;
import com.example.weather.entity.City;
import com.example.weather.entity.User;
import com.example.weather.entity.Weather;
import com.example.weather.exception.CityNotFoundException;
import com.example.weather.exception.IdNotFoundException;
import com.example.weather.exception.WeatherExceptionHandler;
import com.example.weather.service.CityService;
import com.example.weather.service.UserService;
import com.example.weather.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

/**
 * Controller class responsible for handling weather-related operations.
 */
@RestController
@RequestMapping("/weather")
public class WeatherController {

  @Value("${weatherbit.api-key}")
  private String apiKey;
  private static final Logger log = LoggerFactory.getLogger(WeatherController.class);
  final Cache<String, List<WeatherDto>> cache;
  final WeatherExceptionHandler weatherExceptionHandler;
  final CityService cityService;
  private final WeatherService weatherService;
  private final UserService userService;

  /**
   * Constructor for the WeatherController class.
   *
   * @param weatherService          The service responsible for weather-related operations.
   * @param cityService             The service responsible for city-related operations.
   * @param userService             The service responsible for user-related operations.
   * @param cache                   The cache used to store and retrieve weather information.
   * @param weatherExceptionHandler The handler for weather-related exceptions.
   */
  public WeatherController(WeatherService weatherService,
                           CityService cityService,
                           UserService userService,
                           Cache<String, List<WeatherDto>> cache,
                           WeatherExceptionHandler weatherExceptionHandler) {
    this.weatherService = weatherService;
    this.cityService = cityService;
    this.userService = userService;
    this.cache = cache;
    this.weatherExceptionHandler = weatherExceptionHandler;
  }

  /**
   * Handles the creation of weather data.
   *
   * @param weather The Weather entity to be created.
   * @return ResponseEntity indicating the success or failure of the operation.
   */
  @PostMapping
  public ResponseEntity<String> weatherResponse(@RequestBody Weather weather) {
    log.info("post endpoint /weather was called");
    try {
      weatherService.weatherResponse(weather);
      log.info("weather was created successfully");
      return ResponseEntity.ok("Weather was saved successfully!");
    } catch (Exception e) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @PostMapping("/bulk/{city}")
  public ResponseEntity<String> createWeathersBulk(@RequestBody List<Weather> weathers,
                                                   @PathVariable("city") String city) {
    log.info("post endpoint /bulk/{city} was called");

    try {
      weatherService.createWeatherBulk(weathers, city);
      log.info("weather was created successfully");
      return ResponseEntity.ok("Weathers was saved successfully!");
    } catch (Exception e) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  /**
   * Retrieves weather information from the database for a specified city.
   *
   * @param city The name of the city for which weather information is requested.
   * @return ResponseEntity containing the weather information.
   */
  @GetMapping("/db/{city}")
  public ResponseEntity getWeatherDb(@PathVariable String city) {
    log.info("get endpoint /db/{city} was called");
    try {
      if (cache.containsKey(city)) {
        return ResponseEntity.ok(cache.get(city));
      }
      cache.put(city, weatherService.getWeather(city));
      log.info("weather information retrieved successfully.");
      return ResponseEntity.ok(weatherService.getWeather(city));
    } catch (CityNotFoundException e) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    } catch (Exception e) {
      throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  /**
   * Retrieves weather information from the database using a query parameter for the city name.
   *
   * @param cityName The name of the city for which weather information is requested.
   * @return ResponseEntity containing the weather information.
   */
  @GetMapping("/useful")
  public ResponseEntity getWeatherQueryDb(@RequestParam("cityName") String cityName) {
    log.info("get endpoint /useful was called");
    try {
      if (cache.containsKey(cityName)) {
        return ResponseEntity.ok(cache.get(cityName));
      }
      cache.put(cityName, weatherService.getWeatherDb(cityName));
      log.info("weather information retrieved successfully.");
      return ResponseEntity.ok(cache.get(cityName));
    } catch (CityNotFoundException e) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    } catch (Exception e) {
      throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  /**
   * Retrieves weather information from an external API for a specified city
   * and updates the database.
   *
   * @param cityName The name of the city for which weather information is requested.
   * @return ResponseEntity containing the weather information.
   */
  @GetMapping("/city")
  public ResponseEntity getWeather(@RequestParam String cityName) {
    log.info("get endpoint /city was called");
    try {
      String apiUrl = "https://api.weatherbit.io/v2.0/current?key=" + apiKey + "&include=minutely&City=" + cityName;
      Weather weatherEntity = weatherService.processApiUrl(apiUrl);

      City city = cityService.findCityByCityName(cityName);
      if (city != null) {
        city.getWeatherList().add(weatherEntity);
        weatherEntity.setCity(city);
      }

      List<User> users = userService.getAllUsers(weatherEntity.getCountryCode());
      for (User user : users) {
        user.getWeatherList().add(weatherEntity);
        weatherEntity.getUserList().add(user);
      }

      cache.put(cityName, city.getWeatherList().stream().map(WeatherDto::toModel).toList());
      weatherService.weatherResponse(weatherEntity);
      log.info("weather information retrieved successfully.");
      return ResponseEntity.ok(WeatherDto.toModel(weatherEntity));
    } catch (Exception e) {
      throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  /**
   * Deletes weather information based on the provided ID.
   *
   * @param id The ID of the weather entity to be deleted.
   * @return ResponseEntity indicating the success or failure of the deletion.
   */
  @DeleteMapping("/del/{id}")
  public ResponseEntity<String> deleteWeather(@PathVariable Long id) {
    log.info("delete endpoint /del/{id} was called");
    try {
      weatherService.delete(id);
      log.info("Weather with ID {} deleted successfully!", id);
      return ResponseEntity.ok("Deleted successfully!");
    } catch (IdNotFoundException e) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    } catch (Exception e) {
      throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  /**
   * Updates weather information based on the provided ID.
   *
   * @param id      The ID of the weather entity to be updated.
   * @param weather The updated Weather entity.
   * @return ResponseEntity indicating the success or failure of the update.
   */
  @PutMapping("/update/id/")
  public ResponseEntity<String> updateWeather(@RequestParam Long id, @RequestBody Weather weather) {
    log.info("delete endpoint /del/{id} was called");
    try {
      weatherService.complete(id, weather);
      log.info("weather with ID {} updated successfully", id);
      return ResponseEntity.ok("Updated successfully!");
    } catch (Exception e) {
      throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }
}
