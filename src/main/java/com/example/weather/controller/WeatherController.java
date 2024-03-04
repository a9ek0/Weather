package com.example.weather.controller;

import com.example.weather.entity.City;
import com.example.weather.entity.Weather;
import com.example.weather.exception.CityNotFoundException;
import com.example.weather.dto.WeatherDTO;
import com.example.weather.service.CityService;
import com.example.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Value("${weatherbit.api-key}")
    private String apiKey;

    final
    CityService cityService;
    private static final String ERROR_MESSAGE = "Error occurred!";
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService, CityService cityService) {
        this.weatherService = weatherService;
        this.cityService = cityService;
    }

    @PostMapping
    public ResponseEntity<String> weatherResponse(@RequestBody Weather weather) {
        try {
            weatherService.weatherResponse(weather);
            return ResponseEntity.ok("Weather was saved successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/db/{city}")
    public ResponseEntity getWeatherDB(@PathVariable String city) {
        try {
            return ResponseEntity.ok(weatherService.getWeather(city));
        } catch (CityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ERROR_MESSAGE);
        }
    }

    @GetMapping("/city")
    public ResponseEntity<WeatherDTO> getWeather(@RequestParam String cityName) {
        try {
            String apiUrl = "https://api.weatherbit.io/v2.0/current?key=" + apiKey + "&include=minutely&City=" + cityName;
            Weather weatherEntity = weatherService.processApiUrl(apiUrl);

            City city = cityService.findCityByCityName(cityName);
            if (city != null) {
                city.getWeatherList().add(weatherEntity);
                weatherEntity.setCity(city);
            }

            weatherService.weatherResponse(weatherEntity);
            return ResponseEntity.ok(WeatherDTO.toModel(weatherEntity));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWeather(@PathVariable Long id) {
        try {
            weatherService.delete(id);
            return ResponseEntity.ok("Deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ERROR_MESSAGE);
        }
    }

    @PutMapping("/update/id/")
    public ResponseEntity<String> updateWeather(@RequestParam Long id, @RequestBody Weather weather) {
        try {
            weatherService.complete(id, weather);
            return ResponseEntity.ok("Updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ERROR_MESSAGE);
        }
    }
}
