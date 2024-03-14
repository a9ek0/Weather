package com.example.weather.controller;

import com.example.weather.dto.UserDto;
import com.example.weather.entity.User;
import com.example.weather.entity.Weather;
import com.example.weather.exception.IdNotFoundException;
import com.example.weather.exception.UserNotFoundException;
import com.example.weather.service.RequestCounterService;
import com.example.weather.service.UserService;
import com.example.weather.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

  @Mock
  private UserService userService;
  @Mock
  private WeatherService weatherService;
  @Mock
  private RequestCounterService requestCounterService;
  @InjectMocks
  private UserController userController;

  @Test
  void testUserResponse_Success() {
    User newUser = new User();

    ResponseEntity<String> response = userController.userResponse(newUser);

    assertEquals("User was saved successfully", response.getBody());
    verify(userService, times(1)).userResponse(newUser);
  }

  @Test
  void testUserResponse_Failure() {
    User newUser = new User();
    doThrow(new RuntimeException("error")).when(userService).userResponse(newUser);

    HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
      userController.userResponse(newUser);
    });

    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    assertEquals("400 error", exception.getMessage());
    verify(userService, times(1)).userResponse(newUser);
  }

  @Test
  void testCreateUserForWeathers_Success() {
    User newUser = new User();
    newUser.setCountryCode("US");

    Weather weather1 = new Weather();
    weather1.setCountryCode("US");

    Weather weather2 = new Weather();
    weather2.setCountryCode("US");

    List<Weather> weathers = new ArrayList<>();
    weathers.add(weather1);
    weathers.add(weather2);

    when(weatherService.getWeatherByCountryCode("US")).thenReturn(weathers);

    ResponseEntity<String> response = userController.createUserForWeathers(newUser);

    assertEquals("User was saved successfully", response.getBody());
    verify(userService, times(1)).userResponse(newUser);
    assertEquals(newUser, weather1.getUserList().get(0));
    assertEquals(newUser, weather2.getUserList().get(0));
  }

  @Test
  void testCreateUserForWeathers_Failure() {
    User newUser = new User();
    newUser.setCountryCode("US");

    when(weatherService.getWeatherByCountryCode("US")).thenThrow(new RuntimeException("error"));

    HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
      userController.createUserForWeathers(newUser);
    });

    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    assertEquals("400 error", exception.getMessage());
    verify(userService, never()).userResponse(newUser);
  }

  @Test
  void testGetUser_Success() throws UserNotFoundException {
    Long userId = 1L;

    User mockUser = new User();
    UserDto user = UserDto.toModel(mockUser);
    when(userService.getUser(userId)).thenReturn(user);

    ResponseEntity responseEntity = userController.getUser(userId);

    assertEquals(ResponseEntity.ok(user), responseEntity);
    verify(userService, times(1)).getUser(userId);
  }

  @Test
  void testGetUser_UserNotFoundException() throws UserNotFoundException {
    // Arrange
    Long userId = 1L; // Замените на реальный ID пользователя

    when(userService.getUser(userId)).thenThrow(new UserNotFoundException("error"));

    // Act & Assert
    HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
      userController.getUser(userId);
    });

    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    assertEquals("400 error", exception.getMessage());
    verify(userService, times(1)).getUser(userId);
  }

  @Test
  void testGetUser_InternalServerError() throws UserNotFoundException {
    Long userId = 1L;

    when(userService.getUser(userId)).thenThrow(new RuntimeException("error"));

    HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
      userController.getUser(userId);
    });

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
    assertEquals("500 error", exception.getMessage());
    verify(userService, times(1)).getUser(userId);
  }

  @Test
  void testDeleteWeather_Success() throws IdNotFoundException {
    Long userId = 1L;

    ResponseEntity responseEntity = userController.deleteUser(userId);

    assertEquals(ResponseEntity.ok("Deleted successfully"), responseEntity);
    verify(userService, times(1)).delete(userId);
  }

  @Test
  void testDeleteWeather_IdNotFoundException() throws IdNotFoundException {
    Long userId = 1L;

    doThrow(new IdNotFoundException("error")).when(userService).delete(userId);

    HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
      userController.deleteUser(userId);
    });

    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    assertEquals("400 error", exception.getMessage());
    verify(userService, times(1)).delete(userId);
  }

  @Test
  void testDeleteWeather_InternalServerError() throws IdNotFoundException {
    Long userId = 1L;

    doThrow(new RuntimeException("error")).when(userService).delete(userId);

    HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
      userController.deleteUser(userId);
    });

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
    assertEquals("500 error", exception.getMessage());
    verify(userService, times(1)).delete(userId);
  }

  @Test
  void testUpdateWeather_Success() {
    Long userId = 1L;
    User updatedUser = new User();

    ResponseEntity responseEntity = userController.updateUser(userId, updatedUser);

    assertEquals(ResponseEntity.ok("Updated successfully"), responseEntity);
    verify(userService, times(1)).complete(userId, updatedUser);
  }

  @Test
  void testUpdateWeather_InternalServerError() {
    Long userId = 1L;
    User updatedUser = new User();

    doThrow(new RuntimeException("error")).when(userService).complete(userId, updatedUser);

    HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
      userController.updateUser(userId, updatedUser);
    });

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
    assertEquals("500 error", exception.getMessage());
    verify(userService, times(1)).complete(userId, updatedUser);
  }
}

