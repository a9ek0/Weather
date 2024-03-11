package com.example.weather.dto;

import com.example.weather.entity.User;

/**
 * DTO class representing basic information about a User.
 */
public class UserBasicDto {
  private String countryCode;
  private String name;
  private String email;

  /**
   * Converts a User entity to a UserBasicDto model.
   *
   * @param user The User entity to convert.
   * @return UserBasicDto model.
   */
  public static UserBasicDto toModel(User user) {
    UserBasicDto model = new UserBasicDto();

    model.setCountryCode(user.getCountryCode());
    model.setEmail(user.getEmail());
    model.setName(user.getName());

    return model;
  }

  public UserBasicDto() {
    // No initialization logic needed for this constructor
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
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
