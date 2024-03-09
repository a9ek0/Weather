package com.example.weather.controller;

import com.example.weather.exception.WeatherExceptionHandler;
import com.example.weather.exception.CityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import com.example.weather.exception.IdNotFoundException;
import com.example.weather.service.WeatherService;
import org.springframework.web.bind.annotation.*;
import com.example.weather.service.CityService;
import com.example.weather.service.UserService;
import org.springframework.http.ResponseEntity;
import com.example.weather.component.Cache;
import com.example.weather.entity.Weather;
import com.example.weather.dto.WeatherDTO;
import com.example.weather.entity.City;
import com.example.weather.entity.User;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Value("${weatherbit.api-key}")
    private String apiKey;
    private static final Logger log = LoggerFactory.getLogger(WeatherController.class);
    final
    Cache<String, List<WeatherDTO>> cache;
    final
    WeatherExceptionHandler weatherExceptionHandler;
    final
    CityService cityService;
    private static final String ERROR_MESSAGE = "Error occurred!";
    private final WeatherService weatherService;
    private final UserService userService;

    public WeatherController(WeatherService weatherService, CityService cityService, UserService userService, Cache<String, List<WeatherDTO>> cache, WeatherExceptionHandler weatherExceptionHandler) {
        this.weatherService = weatherService;
        this.cityService = cityService;
        this.userService = userService;
        this.cache = cache;
        this.weatherExceptionHandler = weatherExceptionHandler;
    }

    @PostMapping
    public ResponseEntity<String> weatherResponse(@RequestBody Weather weather) {
        try {

            weatherService.weatherResponse(weather);

            log.info("Weather was created successfully!");
            return ResponseEntity.ok("Weather was saved successfully!");
        } catch (Exception e) {
            log.error("Error creating weather!");

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/db/{city}")
    public ResponseEntity getWeatherDB(@PathVariable String city) {
        log.info("Processing request for data");

        try {
            if(cache.containsKey(city)){
                return ResponseEntity.ok(cache.get(city));
            }

            cache.put(city, weatherService.getWeather(city));

            log.info("Data processing successful");
            return ResponseEntity.ok(weatherService.getWeather(city));
        } catch (CityNotFoundException e) {
            log.warn("Error processing data. {}", e.getMessage());
            return weatherExceptionHandler.handleBadRequest(e);
        } catch (Exception e) {
            log.error("Error processing data");
            return weatherExceptionHandler.handleInternalServerError(e);
        }
    }

    @GetMapping("/db/city")
    public ResponseEntity getWeatherQueryDB(@RequestParam("cityName") String cityName) {
        log.info("Processing request for data");

        try {
            if(cache.containsKey(cityName)){
                return ResponseEntity.ok(cache.get(cityName));
            }

            cache.put(cityName, weatherService.getWeatherDB(cityName));
            log.info("Data processing successful");
            return ResponseEntity.ok(cache.get(cityName));
        } catch (CityNotFoundException e) {
            log.warn("Error processing data. {}", e.getMessage());
            return weatherExceptionHandler.handleBadRequest(e);
        } catch (Exception e) {
            log.error("Error processing data", e);
            return weatherExceptionHandler.handleInternalServerError(e);
        }
    }

    @GetMapping("/city")
    public ResponseEntity getWeather(@RequestParam String cityName) {
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

            cache.put(cityName, city.getWeatherList().stream().map(WeatherDTO::toModel).toList());
            weatherService.weatherResponse(weatherEntity);
            log.info("Weather information retrieved successfully!");
            return ResponseEntity.ok(WeatherDTO.toModel(weatherEntity));
        } catch (Exception e) {
            log.error("Error processing data");
            return weatherExceptionHandler.handleInternalServerError(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWeather(@PathVariable Long id) {
        try {
            weatherService.delete(id);

            log.info("Weather with ID {} deleted successfully!", id);
            return ResponseEntity.ok("Deleted successfully!");
        } catch (IdNotFoundException e) {
            log.warn("Error deleting weather with ID {}. {}", id, e.getMessage());

            return weatherExceptionHandler.handleBadRequest(e);
        } catch (Exception e) {
            log.error("Error deleting weather");

            return weatherExceptionHandler.handleInternalServerError(e);
        }
    }

    @PutMapping("/update/id/")
    public ResponseEntity<String> updateWeather(@RequestParam Long id, @RequestBody Weather weather) {
        try {
            weatherService.complete(id, weather);

            log.info("Weather with ID {} updated successfully!", id);
            return ResponseEntity.ok("Updated successfully!");
        } catch (Exception e) {
            log.error("Error updating weather.");
            return weatherExceptionHandler.handleInternalServerError(e);
            //return ResponseEntity.badRequest().body(ERROR_MESSAGE);
        }
    }
}
