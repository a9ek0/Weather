package com.example.weather.dto;

import com.example.weather.entity.User;
import com.example.weather.entity.Weather;
import com.example.weather.service.DateTimeService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WeatherDtoTest {

  @Test
  void testToModel() {
    Weather weather = new Weather();
    weather.setCityName("TestCity");
    weather.setTemp(25.5);
    weather.setDescription("Sunny");
    weather.setRh(60.0);
    weather.setCountryCode("US");
    weather.setDateTime(DateTimeService.toDateTime("2022-03-04 12:00"));

    List<User> userList = new ArrayList<>();
    User userDto = new User();
    userDto.setName("testUser");
    userList.add(userDto);

    weather.setUserList(userList);

    WeatherDto weatherDto = WeatherDto.toModel(weather);

    assertEquals("TestCity", weatherDto.getCityName());
    assertEquals(25.5, weatherDto.getTemp());
    assertEquals("Sunny", weatherDto.getDescription());
    assertEquals(60.0, weatherDto.getRh());
    assertEquals("US", weatherDto.getCountryCode());
    assertEquals("2022-03-04 12:00", weatherDto.getDateTime());

    List<UserBasicDto> userDtoList = weatherDto.getUserBasicDtoList();
    assertEquals(1, userDtoList.size());
    UserBasicDto retrievedUserDto = userDtoList.get(0);
    assertEquals("testUser", retrievedUserDto.getName());
  }

  @Test
  void testGettersAndSetters() {
    WeatherDto weatherDto = new WeatherDto();

    weatherDto.setCityName("TestCity");
    weatherDto.setTemp(25.5);
    weatherDto.setDescription("Sunny");
    weatherDto.setRh(60.0);
    weatherDto.setCountryCode("US");
    weatherDto.setDateTime("2022-03-04T12:00:00");

    List<UserBasicDto> userDtoList = new ArrayList<>();
    UserBasicDto userDto = new UserBasicDto();
    userDto.setName("testUser");
    userDtoList.add(userDto);
    weatherDto.setUserBasicDtoList(userDtoList);

    assertEquals("TestCity", weatherDto.getCityName());
    assertEquals(25.5, weatherDto.getTemp());
    assertEquals("Sunny", weatherDto.getDescription());
    assertEquals(60.0, weatherDto.getRh());
    assertEquals("US", weatherDto.getCountryCode());
    assertEquals("2022-03-04T12:00:00", weatherDto.getDateTime());

    List<UserBasicDto> retrievedUserDtoList = weatherDto.getUserBasicDtoList();
    assertEquals(1, retrievedUserDtoList.size());
    UserBasicDto retrievedUserDto = retrievedUserDtoList.get(0);
    assertEquals("testUser", retrievedUserDto.getName());
  }
}
