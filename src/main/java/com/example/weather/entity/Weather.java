package com.example.weather.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing weather information.
 */
@Entity
@Table
public class Weather {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String description;
  private String cityName;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime dateTime;
  private String countryCode;
  private Double temp;
  private Double rh;

  @ManyToOne
  @JoinColumn(name = "cityId")
  private City city;

  @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(name = "WEATHER_USER_MAPPING",
          joinColumns = @JoinColumn(name = "weatherId"),
          inverseJoinColumns = @JoinColumn(name = "userId"))
  private List<User> userList = new ArrayList<>();

  public Weather() {
  }

  /**
   * Constructor for creating a Weather entity from JSON data.
   *
   * @param jsonNode The JSON data representing weather information.
   */
  public Weather(JsonNode jsonNode) {
    this.description = jsonNode.get("weather").get("description").asText();
    this.countryCode = jsonNode.get("country_code").asText();
    this.cityName = jsonNode.get("city_name").asText();

    String pattern = "yyyy-MM-dd HH:mm";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    this.dateTime = LocalDateTime.parse((jsonNode.get("ob_time").asText()), formatter);

    this.temp = jsonNode.get("temp").asDouble();
    this.rh = jsonNode.get("rh").asDouble();
  }

  public String getCityName() {
    return cityName;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public List<User> getUserList() {
    return userList;
  }

  public void setUserList(List<User> userList) {
    this.userList = userList;
  }

  public City getCity() {
    return city;
  }

  public void setCity(City city) {
    this.city = city;
  }
}
