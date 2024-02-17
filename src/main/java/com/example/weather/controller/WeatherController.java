package com.example.weather.controller;

import com.example.weather.entity.WeatherEntity;
import com.example.weather.model.Weather;
import com.example.weather.service.WeatherService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Value("${weatherbit.api-key}")
    private String apiKey;


    @Autowired
    private WeatherService weatherService;

    @PostMapping
    public ResponseEntity weatherResponse(@RequestBody WeatherEntity weather){
        try {
            weatherService.weatherResponse(weather);
            return ResponseEntity.ok("Weather was saved successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/city")
    public ResponseEntity getWeather(@RequestParam String city) {
        try {
            String apiUrl = "https://api.weatherbit.io/v2.0/current?key=" + apiKey + "&include=minutely&City=" + city;

            RestTemplate restTemplate = new RestTemplate();

            String jsonString = restTemplate.getForObject(apiUrl, String.class);

            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode jsonNode = objectMapper.readTree(jsonString);
            JsonNode dataNode = jsonNode.get("data").get(0);

            WeatherEntity weatherEntity = new WeatherEntity(dataNode);

            weatherService.weatherResponse(weatherEntity);

            return ResponseEntity.ok(Weather.toModel(weatherEntity));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteWeather(@PathVariable Long id) {
        try {
            weatherService.delete(id);
            return ResponseEntity.ok("Deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error occurred!");
        }
    }
}
