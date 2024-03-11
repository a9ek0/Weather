package com.example.weather.dto;

import com.example.weather.entity.Weather;
import com.example.weather.service.DateTimeService;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object (DTO) for representing weather information.
 */
public class WeatherDto {
  private String cityName;
  private String dateTime;
  private String description;
  private double temp;
  private double rh;
  private String countryCode;
  private List<UserBasicDto> userBasicDtoList = new ArrayList<>();

  /**
   * Converts a Weather entity to a WeatherDto model.
   *
   * @param weather The Weather entity to convert.
   * @return A WeatherDto model representing the weather information.
   */
  public static WeatherDto toModel(Weather weather) {
    WeatherDto model = new WeatherDto();

    model.setTemp(weather.getTemp());
    model.setDescription(weather.getDescription());
    model.setRh(weather.getRh());
    model.setCityName(weather.getCityName());
    model.setDateTime(DateTimeService.toString(weather.getDateTime()));
    model.setCountryCode(weather.getCountryCode());
    if (weather.getUserList() != null) {
      model.setUserBasicDtoList(weather.getUserList().stream().map(UserBasicDto::toModel).toList());
    }
    return model;
  }


  /**
   * Empty constructor for the Weather class.
   *
   * <p>
   * This constructor is provided to meet the requirements of frameworks
   * like Spring, which may rely on a default constructor for certain operations.
   * It is intentionally left empty as the initialization logic is not needed.
   * </p>
   */
  public WeatherDto() {
    // No initialization logic needed for this constructor
  }

  public String getCityName() {
    return cityName;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  public String getDateTime() {
    return dateTime;
  }

  public void setDateTime(String dateTime) {
    this.dateTime = dateTime;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getTemp() {
    return temp;
  }

  public void setTemp(double temp) {
    this.temp = temp;
  }

  public double getRh() {
    return rh;
  }

  public void setRh(double rh) {
    this.rh = rh;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public List<UserBasicDto> getUserBasicDtoList() {
    return userBasicDtoList;
  }

  public void setUserBasicDtoList(List<UserBasicDto> userBasicDtoList) {
    this.userBasicDtoList = userBasicDtoList;
  }
}
