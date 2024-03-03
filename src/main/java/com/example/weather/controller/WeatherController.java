package com.example.weather.controller;

import com.example.weather.entity.Weather;
import com.example.weather.entity.WeatherHistory;
import com.example.weather.exception.CityNotFoundException;
import com.example.weather.dto.WeatherDTO;
import com.example.weather.service.WeatherHistoryService;
import com.example.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Value("${weatherbit.api-key}")
    private String apiKey;


    private final WeatherHistoryService weatherHistoryService;

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService, WeatherHistoryService weatherHistoryService) {
        this.weatherService = weatherService;
        this.weatherHistoryService = weatherHistoryService;
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

                tmpWeather.setDescription(weatherEntity.getDescription());
                tmpWeather.setRh(weatherEntity.getRh());
                tmpWeather.setDateTime(weatherEntity.getDateTime());
                tmpWeather.setTemp(weatherEntity.getTemp());
                tmpWeather.setCountryCode(weatherEntity.getCountryCode());
                tmpWeather.setCityName(weatherEntity.getCityName());

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

    @PutMapping("/update/id/")
    public ResponseEntity<String> updateWeather(@RequestParam Long id, @RequestBody Weather weather){
        try {
            weatherService.complete(id, weather);
            return ResponseEntity.ok("Updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error occurred!");
        }
    }
}
