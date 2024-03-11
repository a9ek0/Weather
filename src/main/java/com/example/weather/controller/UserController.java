package com.example.weather.controller;

import com.example.weather.entity.User;
import com.example.weather.entity.Weather;
import com.example.weather.exception.IdNotFoundException;
import com.example.weather.exception.UserNotFoundException;
import com.example.weather.exception.WeatherExceptionHandler;
import com.example.weather.service.UserService;
import com.example.weather.service.WeatherService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for managing User-related operations.
 */
@RestController
@RequestMapping("/weather/user")
public class UserController {
  private static final String ERROR_MESSAGE = "Error occurred!";
  private static final Logger log = LoggerFactory.getLogger(UserController.class);
  private final UserService userService;
  private final WeatherService weatherService;
  final WeatherExceptionHandler weatherExceptionHandler;

  /**
   * Constructor for UserController.
   *
   * @param userService             The UserService instance.
   * @param weatherService          The WeatherService instance.
   * @param weatherExceptionHandler The WeatherExceptionHandler instance.
   */
  public UserController(UserService userService,
                        WeatherService weatherService,
                        WeatherExceptionHandler weatherExceptionHandler) {
    this.userService = userService;
    this.weatherService = weatherService;
    this.weatherExceptionHandler = weatherExceptionHandler;
  }

  /**
   * Endpoint for creating a new user.
   *
   * @param user The User object to be created.
   * @return ResponseEntity indicating the success or failure of the operation.
   */
  @PostMapping
  public ResponseEntity<String> userResponse(@RequestBody User user) {
    log.info("Data creation processing.");
    try {
      userService.userResponse(user);
      log.info("User was saved successfully.");
      return ResponseEntity.ok("User was saved successfully!");
    } catch (Exception e) {
      log.error("Error saving user.");
      return weatherExceptionHandler.handleInternalServerError(e);
    }
  }

  /**
   * Endpoint for creating a new user associated with specific weathers.
   *
   * @param user The User object to be created.
   * @return ResponseEntity indicating the success or failure of the operation.
   */
  @PostMapping("/ForWeathers")
  public ResponseEntity<String> createUserForWeathers(@RequestBody User user) {
    log.info("Data creation processing.");
    try {
      List<Weather> weathers = weatherService.getWeatherByCountryCode(user.getCountryCode());
      for (Weather weather : weathers) {
        if (!weather.getUserList().contains(user)) {
          weather.getUserList().add(user);
        }
      }
      userService.userResponse(user);
      log.info("User was created successfully.");
      return ResponseEntity.ok("User was saved successfully!");
    } catch (Exception e) {
      log.error("Error creating user.");
      return weatherExceptionHandler.handleInternalServerError(e);
    }
  }

  /**
   * Endpoint for retrieving user information by ID.
   *
   * @param id The ID of the user.
   * @return ResponseEntity containing user information or an error message.
   */
  @GetMapping("/id/{id}")
  public ResponseEntity getUser(@PathVariable Long id) {
    log.info("Processing request for data.");
    try {
      log.info("Processing request for user with ID {}.", id);
      return ResponseEntity.ok(userService.getUser(id));
    } catch (UserNotFoundException e) {
      log.warn("User with ID {} not found. {}", id, e.getMessage());
      return weatherExceptionHandler.handleBadRequest(e);
    } catch (Exception e) {
      log.error("Error processing request for user with ID {}.", id);
      return weatherExceptionHandler.handleInternalServerError(e);
    }
  }

  /**
   * Endpoint for deleting a user by ID.
   *
   * @param id The ID of the user to be deleted.
   * @return ResponseEntity indicating the success or failure of the operation.
   */
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<String> deleteWeather(@PathVariable Long id) {
    log.info("Data deletion processing.");
    try {
      log.info("Deleting user with ID {}.", id);
      userService.delete(id);
      return ResponseEntity.ok("Deleted successfully.");
    } catch (IdNotFoundException e) {
      log.warn("Error deleting user with ID {}. {}", id, e.getMessage());
      return weatherExceptionHandler.handleBadRequest(e);
    } catch (Exception e) {
      log.error("Error deleting user with ID {}.", id, e);
      return weatherExceptionHandler.handleInternalServerError(e);
    }
  }

  /**
   * Endpoint for updating user information by ID.
   *
   * @param id   The ID of the user to be updated.
   * @param user The updated User object.
   * @return ResponseEntity indicating the success or failure of the operation.
   */
  @PutMapping("/update/{id}")
  public ResponseEntity<String> updateWeather(@PathVariable Long id, @RequestBody User user) {
    log.info("Data update processing.");
    try {
      userService.complete(id, user);
      log.info("User with ID {} updated successfully.", id);
      return ResponseEntity.ok("Updated successfully!");
    } catch (Exception e) {
      log.error("Error updating user with ID {}.", id);
      return weatherExceptionHandler.handleInternalServerError(e);
    }
  }


}
