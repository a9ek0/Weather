package com.example.weather.dto;

import com.example.weather.entity.User;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO class representing user information.
 */
public class UserDto {
  private String countryCode;
  private String name;
  private String email;
  private List<WeatherUsersDto> weatherUsersDtoList = new ArrayList<>();

  /**
   * Converts a User entity to a UserDto model.
   *
   * @param user The User entity to be converted.
   * @return UserDto model.
   */
  public static UserDto toModel(User user) {
    UserDto model = new UserDto();

    model.setCountryCode(user.getCountryCode());
    model.setEmail(user.getEmail());
    model.setName(user.getName());
    if (model.getWeatherUsersDtoList() != null) {
      model.setWeatherUsersDtoList(user.getWeatherList()
                                  .stream().map(WeatherUsersDto::toModel).toList());
    }
    return model;
  }

  public UserDto() {
    // No initialization logic needed for this constructor
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public List<WeatherUsersDto> getWeatherUsersDtoList() {
    return weatherUsersDtoList;
  }

  public void setWeatherUsersDtoList(List<WeatherUsersDto> weatherUsersDtoList) {
    this.weatherUsersDtoList = weatherUsersDtoList;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}

