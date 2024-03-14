package com.example.weather.dto;

import com.example.weather.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserBasicDtoTest {

  @Test
  void testToModel() {
    User user = new User();
    user.setCountryCode("US");
    user.setEmail("user@example.com");
    user.setName("John Doe");

    UserBasicDto userBasicDto = UserBasicDto.toModel(user);

    assertEquals("US", userBasicDto.getCountryCode());
    assertEquals("user@example.com", userBasicDto.getEmail());
    assertEquals("John Doe", userBasicDto.getName());
  }

  @Test
  void testGettersAndSetters() {
    UserBasicDto userBasicDto = new UserBasicDto();

    userBasicDto.setCountryCode("US");
    userBasicDto.setEmail("user@example.com");
    userBasicDto.setName("John Doe");

    assertEquals("US", userBasicDto.getCountryCode());
    assertEquals("user@example.com", userBasicDto.getEmail());
    assertEquals("John Doe", userBasicDto.getName());
  }
}
