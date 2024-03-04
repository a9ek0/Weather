package com.example.weather.controller;

import com.example.weather.entity.User;
import com.example.weather.entity.Weather;
import com.example.weather.exception.UserNotFoundException;
import com.example.weather.service.UserService;
import com.example.weather.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weather/user")
public class UserController {
    private static final String ERROR_MESSAGE = "Error occurred!";
    private final UserService userService;
    private final WeatherService weatherService;

    public UserController(UserService userService, WeatherService weatherService) {
        this.userService = userService;
        this.weatherService = weatherService;
    }

    @PostMapping
    public ResponseEntity<String> userResponse(@RequestBody User user) {
        try {
            userService.userResponse(user);
            return ResponseEntity.ok("User was saved successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/ForWeathers")
    public ResponseEntity<String> createUserForWeathers(@RequestBody User user) {
        try {

            List<Weather> weathers = weatherService.getWeatherByCountryCode(user.getCountryCode());
            for (Weather weather : weathers) {
                if (!weather.getUserList().contains(user)) {
                    weather.getUserList().add(user);
                }
            }

            userService.userResponse(user);
            return ResponseEntity.ok("User was saved successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity getUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getUser(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ERROR_MESSAGE);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteWeather(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.ok("Deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ERROR_MESSAGE);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateWeather(@PathVariable Long id, @RequestBody User user){
        try {
            userService.complete(id, user);
            return ResponseEntity.ok("Updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ERROR_MESSAGE);
        }
    }


}
