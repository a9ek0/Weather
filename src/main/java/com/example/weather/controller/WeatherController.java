package com.example.weather.controller;

import com.example.weather.entity.Weather;
import com.example.weather.entity.WeatherHistory;
import com.example.weather.exception.CityNotFoundException;
import com.example.weather.dto.WeatherDTO;
import com.example.weather.service.WeatherHistoryService;
import com.example.weather.service.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Value("${weatherbit.api-key}")
    private String apiKey;


    @Autowired
    private WeatherHistoryService weatherHistoryService;

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
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
            return ResponseEntity.badRequest().body("Error occurred!");
        }
    }

    @GetMapping("/city")
    public ResponseEntity<WeatherDTO> getWeather(@RequestParam String city) {
        try {
            String apiUrl = "https://api.weatherbit.io/v2.0/current?key=" + apiKey + "&include=minutely&City=" + city;

            Weather weatherEntity = weatherService.processApiUrl(apiUrl);

            Weather tmpWeather = weatherService.findWeather(weatherEntity.getCityName());
            if (tmpWeather != null) {
                weatherHistoryService.createWeatherHistory(new WeatherHistory(), tmpWeather, city);


                weatherService.updateWeather(tmpWeather, null, null,
                        Optional.ofNullable(weatherEntity.getDescription()), Optional.ofNullable(weatherEntity.getCityName()),
                        Optional.ofNullable(weatherEntity.getDatetime()), Optional.ofNullable(weatherEntity.getCountryCode()),
                        Optional.of(weatherEntity.getTemp()), Optional.of(weatherEntity.getRh()));

                weatherService.weatherResponse(tmpWeather);
                return ResponseEntity.ok(WeatherDTO.toModel(tmpWeather));
            } else {
                weatherService.weatherResponse(weatherEntity);
                return ResponseEntity.ok(WeatherDTO.toModel(weatherEntity));
            }

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
            return ResponseEntity.badRequest().body("Error occurred!");
        }
    }
}
