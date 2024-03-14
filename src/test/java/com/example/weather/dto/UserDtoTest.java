package com.example.weather.dto;

import com.example.weather.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDtoTest {

  @Test
  void testToModel() {
    User user = new User();
    user.setCountryCode("US");
    user.setEmail("user@example.com");
    user.setName("John Doe");

    UserDto userDto = UserDto.toModel(user);

    assertEquals("US", userDto.getCountryCode());
    assertEquals("user@example.com", userDto.getEmail());
    assertEquals("John Doe", userDto.getName());
  }

  @Test
  void testGettersAndSetters() {
    UserDto userDto = new UserDto();

    userDto.setCountryCode("US");
    userDto.setEmail("user@example.com");
    userDto.setName("John Doe");

    assertEquals("US", userDto.getCountryCode());
    assertEquals("user@example.com", userDto.getEmail());
    assertEquals("John Doe", userDto.getName());
  }

  @Test
  void testWeatherUsersDtoList() {
    UserDto userDto = new UserDto();

    WeatherUsersDto weatherUsersDto = new WeatherUsersDto();
    userDto.getWeatherUsersDtoList().add(weatherUsersDto);

    assertEquals(1, userDto.getWeatherUsersDtoList().size());
  }
}
