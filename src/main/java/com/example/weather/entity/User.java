package com.example.weather.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing a User.
 */
@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;
  private String email;
  private String countryCode;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "WEATHER_USER_MAPPING",
          joinColumns = @JoinColumn(name = "userId"),
          inverseJoinColumns = @JoinColumn(name = "weatherId"))
  private List<Weather> weatherList = new ArrayList<>();

  public User() {
    // No initialization logic needed for this constructor
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public List<Weather> getWeatherList() {
    return weatherList;
  }

  public void setWeatherList(List<Weather> weatherList) {
    this.weatherList = weatherList;
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
