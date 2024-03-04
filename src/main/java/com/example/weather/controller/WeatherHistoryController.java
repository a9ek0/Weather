package com.example.weather.controller;

import com.example.weather.entity.WeatherHistory;
import com.example.weather.exception.CityNotFoundException;
import com.example.weather.service.WeatherHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/weather/history")
public class WeatherHistoryController {

    private static final String ERROR_MESSAGE = "Error occurred!";
    private final WeatherHistoryService weatherHistoryService;

    public WeatherHistoryController(WeatherHistoryService weatherHistoryService) {
        this.weatherHistoryService = weatherHistoryService;
    }

    @PostMapping("/city")
    public ResponseEntity createWeatherHistory(@RequestBody WeatherHistory weatherHistory,
                                               @RequestParam String city) {
        try {
            return ResponseEntity.ok(weatherHistoryService.createWeatherHistory(weatherHistory, city));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ERROR_MESSAGE);
        }
    }

    @PutMapping("/update")
    public ResponseEntity createWeatherHistory(@RequestParam String countryCode,
                                               @RequestParam String description,
                                               @RequestParam LocalDateTime dataTime,
                                               @RequestParam double temp,
                                               @RequestParam double rh,
                                               @RequestParam Long   id) {
        try {
            return ResponseEntity.ok(weatherHistoryService.complete(id, countryCode, temp, rh, dataTime, description));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ERROR_MESSAGE);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteWeather(@PathVariable Long id) {
        try {
            weatherHistoryService.delete(id);
            return ResponseEntity.ok("Deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ERROR_MESSAGE);
        }
    }

    @GetMapping("/city/")
    public ResponseEntity getWeather(@RequestParam String city) {
        try {
            return ResponseEntity.ok(weatherHistoryService.getWeather(city));
        } catch (CityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ERROR_MESSAGE);
        }
    }
}
