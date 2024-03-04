package com.example.weather.controller;

import com.example.weather.entity.City;
import com.example.weather.entity.Weather;
import com.example.weather.exception.CityAlreadyExistsException;
import com.example.weather.exception.UserNotFoundException;
import com.example.weather.service.CityService;
import com.example.weather.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<String> cityResponse(@RequestBody City city) {
        try {
            if (cityService.findCityByCityName(city.getName()) != null) {
                throw new CityAlreadyExistsException("City already exists!");
            }

            List<Weather> weathers = weatherService.findWeather(city.getName());
            for (Weather weather : weathers) {
                city.getWeatherList().add(weather);
                weather.setCity(city);
            }

            cityService.cityResponse(city);
            return ResponseEntity.ok("City was saved successfully!");
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
    public ResponseEntity<String> updateWeather(@PathVariable Long id, @RequestBody City city) {
        try {
            if (cityService.findCityByCityName(city.getName()) != null)
                throw new CityAlreadyExistsException("City already exists!");

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
            return ResponseEntity.ok("Updated successfully!");
        } catch (CityAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ERROR_MESSAGE);
        }
    }
}
