package com.example.weather.controller;

import com.example.weather.entity.City;
import com.example.weather.exception.UserNotFoundException;
import com.example.weather.service.CityService;
import com.example.weather.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather/city")
public class CityController {
    private static final String ERROR_MESSAGE = "Error occurred!";

    final
    WeatherService weatherService;
    final
    CityService cityService;

    public CityController(CityService cityService, WeatherService weatherService) {
        this.cityService = cityService;
        this.weatherService = weatherService;
    }

    @PostMapping
    public ResponseEntity<String> userResponse(@RequestBody City city) {
        try {
            cityService.cityResponse(city);

            city.getWeatherList().add(weatherService.findWeather(city.getName()));

            weatherService.findWeather(city.getName()).setCity(city);

            return ResponseEntity.ok("User was saved successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity getUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(cityService.getCity(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ERROR_MESSAGE);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteWeather(@PathVariable Long id) {
        try {
            cityService.delete(id);
            return ResponseEntity.ok("Deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ERROR_MESSAGE);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateWeather(@PathVariable Long id, @RequestBody City city){
        try {
            cityService.complete(id, city);
            return ResponseEntity.ok("Updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ERROR_MESSAGE);
        }
    }
}
