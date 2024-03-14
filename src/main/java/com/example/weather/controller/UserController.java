package com.example.weather.controller;

import com.example.weather.entity.User;
import com.example.weather.entity.Weather;
import com.example.weather.exception.IdNotFoundException;
import com.example.weather.exception.UserNotFoundException;
import com.example.weather.exception.WeatherExceptionHandler;
import com.example.weather.service.RequestCounterService;
import com.example.weather.service.UserService;
import com.example.weather.service.WeatherService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Controller class for managing User-related operations.
 */
@RestController
@RequestMapping("/weather/user")
public class UserController {
  private static final Logger log = LoggerFactory.getLogger(UserController.class);
  private final UserService userService;
  private final WeatherService weatherService;
  final WeatherExceptionHandler weatherExceptionHandler;
  final RequestCounterService requestCounterService;

  /**
   * Constructor for UserController.
   *
   * @param userService             The UserService instance.
   * @param weatherService          The WeatherService instance.
   * @param weatherExceptionHandler The WeatherExceptionHandler instance.
   */
  public UserController(UserService userService,
                        WeatherService weatherService,
                        WeatherExceptionHandler weatherExceptionHandler,
                        RequestCounterService requestCounterService) {
    this.userService = userService;
    this.weatherService = weatherService;
    this.weatherExceptionHandler = weatherExceptionHandler;
    this.requestCounterService = requestCounterService;
  }

  /**
   * Endpoint for creating a new user.
   *
   * @param user The User object to be created.
   * @return ResponseEntity indicating the success or failure of the operation.
   */
  @PostMapping
  @CrossOrigin
  public ResponseEntity<String> userResponse(@RequestBody User user) {
    log.info("post endpoint /weather was called");
    requestCounterService.increment();
    try {
      userService.userResponse(user);
      log.info("user was saved successfully");
      return ResponseEntity.ok("User was saved successfully");
    } catch (Exception e) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  /**
   * Endpoint for creating a new user associated with specific weathers.
   *
   * @param user The User object to be created.
   * @return ResponseEntity indicating the success or failure of the operation.
   */
  @PostMapping("/forWeathers")
  @CrossOrigin
  public ResponseEntity<String> createUserForWeathers(@RequestBody User user) {
    log.info("post endpoint /weather was called");
    requestCounterService.increment();
    try {
      List<Weather> weathers = weatherService.getWeatherByCountryCode(user.getCountryCode());
      for (Weather weather : weathers) {
        if (!weather.getUserList().contains(user)) {
          weather.getUserList().add(user);
        }
      }
      userService.userResponse(user);
      log.info("user was created successfully");
      return ResponseEntity.ok("User was saved successfully");
    } catch (Exception e) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  /**
   * Endpoint for retrieving user information by ID.
   *
   * @param id The ID of the user.
   * @return ResponseEntity containing user information or an error message.
   */
  @GetMapping("/id/{id}")
  @CrossOrigin
  public ResponseEntity getUser(@PathVariable Long id) {
    log.info("get endpoint /id/{id} was called");
    requestCounterService.increment();
    try {
      log.info("user with ID {} retrieved successfully", id);
      return ResponseEntity.ok(userService.getUser(id));
    } catch (UserNotFoundException e) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    } catch (Exception e) {
      throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  /**
   * Endpoint for deleting a user by ID.
   *
   * @param id The ID of the user to be deleted.
   * @return ResponseEntity indicating the success or failure of the operation.
   */
  @DeleteMapping("/delete/{id}")
  @CrossOrigin
  public ResponseEntity<String> deleteUser(@PathVariable Long id) {
    log.info("delete endpoint /delete/{id} was called");
    requestCounterService.increment();
    try {
      log.info("deleting user with ID {}", id);
      userService.delete(id);
      log.info("user deleted successfully");
      return ResponseEntity.ok("Deleted successfully");
    } catch (IdNotFoundException e) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    } catch (Exception e) {
      throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }

  /**
   * Endpoint for updating user information by ID.
   *
   * @param id   The ID of the user to be updated.
   * @param user The updated User object.
   * @return ResponseEntity indicating the success or failure of the operation.
   */
  @PutMapping("/update/id/")
  @CrossOrigin
  public ResponseEntity<String> updateUser(@RequestParam Long id, @RequestBody User user) {
    log.info("update endpoint /update/{id} was called");
    requestCounterService.increment();
    try {
      userService.complete(id, user);
      log.info("user with ID {} updated successfully", id);
      return ResponseEntity.ok("Updated successfully");
    } catch (Exception e) {
      throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }


}
