package com.example.weather.controller;

import com.example.weather.entity.WeatherHistory;
import com.example.weather.service.WeatherHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather/history")
public class WeatherHistoryController {

    @Autowired
    private WeatherHistoryService weatherHistoryService;
    @PostMapping("/city")
    public ResponseEntity createWeatherHistory(@RequestBody WeatherHistory weatherHistory,
                                               @RequestParam String city) {
        try {
            return ResponseEntity.ok(weatherHistoryService.createWeatherHistory(weatherHistory, city));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error occurred!");
        }
    }

    @PutMapping("/update")
    public ResponseEntity createWeatherHistory(@RequestParam String countryCode,
                                               @RequestParam String description,
                                               @RequestParam String dataTime,
                                               @RequestParam double temp,
                                               @RequestParam double rh,
                                               @RequestParam Long   id) {
        try {
            return ResponseEntity.ok(weatherHistoryService.complete(id, countryCode, temp, rh, dataTime, description));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error occurred!");
        }
    }

}
