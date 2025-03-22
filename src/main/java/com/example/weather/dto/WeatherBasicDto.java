package com.example.weather.dto;

import com.example.weather.entity.Weather;
import com.example.weather.service.DateTimeService;

/**
 * A simplified representation of Weather used for basic information.
 */
public class WeatherBasicDto {
  private String dateTime;
  private String description;
  private double temp;
  private double rh;
  private String countryCode;

  /**
   * Converts a Weather entity to its basic DTO representation.
   *
   * @param weather The Weather entity to convert.
   * @return The WeatherBasicDto representing basic information.
   */
  public static WeatherBasicDto toModel(Weather weather) {
    WeatherBasicDto model = new WeatherBasicDto();

    model.setTemp(weather.getTemp());
    model.setDescription(weather.getDescription());
    model.setRh(weather.getRh());
    model.setDateTime(DateTimeService.toString(weather.getDateTime()));
    model.setCountryCode(weather.getCountryCode());

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
  public WeatherBasicDto() {
    // No initialization logic needed for this constructor
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
}
