package com.example.weather.dto;

import com.example.weather.entity.City;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object (DTO) representing information about a city.
 * Used to transfer simplified city data between layers of the application.
 */
public class CityDto {
  private String name;
  private List<WeatherBasicDto> weatherBasicDtos = new ArrayList<>();

  /**
   * Default constructor with no initialization logic.
   */
  public CityDto() {
    // No initialization logic needed for this constructor
  }

  /**
   * Converts a City entity to a CityDto model.
   *
   * @param city The City entity to convert.
   * @return CityDto model with simplified city information.
   */
  public static CityDto toModel(City city) {
    CityDto model = new CityDto();

    model.setName(city.getName());
    if (city.getWeatherList() != null) {
      model.setWeatherBasicDtos(city.getWeatherList().stream()
                                .map(WeatherBasicDto::toModel).toList());
    }

    return model;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<WeatherBasicDto> getWeatherBasicDtos() {
    return weatherBasicDtos;
  }

  public void setWeatherBasicDtos(List<WeatherBasicDto> weatherBasicDtos) {
    this.weatherBasicDtos = weatherBasicDtos;
  }
}
