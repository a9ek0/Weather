package com.example.weather.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    private String cityName;
    private String datetime;
    private String countryCode;
    private double temp;
    private double rh;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "weather")
    private List<WeatherHistory> weatherHistoryList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "WEATHER_USER_MAPPING",
            joinColumns = @JoinColumn(name  = "weatherId"),
            inverseJoinColumns = @JoinColumn(name = "userId"))
    private List<User> userList = new ArrayList<>();

    public Weather() {
    }

    public Weather(JsonNode jsonNode) {
        this.description = jsonNode.get("weather").get("description").asText();
        this.countryCode = jsonNode.get("country_code").asText();
        this.cityName = jsonNode.get("city_name").asText();
        this.datetime = jsonNode.get("ob_time").asText();
        this.temp = jsonNode.get("temp").asDouble();
        this.rh = jsonNode.get("rh").asDouble();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
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

    public List<WeatherHistory> getWeatherHistoryList() {
        return weatherHistoryList;
    }

    public void setWeatherHistoryList(List<WeatherHistory> weatherHistoryList) {
        this.weatherHistoryList = weatherHistoryList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

}
