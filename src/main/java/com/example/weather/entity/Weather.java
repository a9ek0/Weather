package com.example.weather.entity;

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
    private String dateTime;
    private String countryCode;
    private Double temp;
    private Double rh;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "weather")
    private List<WeatherHistory> weatherHistoryList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "WEATHER_USER_MAPPING",
            joinColumns = @JoinColumn(name = "weatherId"),
            inverseJoinColumns = @JoinColumn(name = "userId"))
    private List<User> userList = new ArrayList<>();

    public Weather() {
    }

    public Weather(JsonNode jsonNode) {
        this.description = jsonNode.get("weather").get("description").asText();
        this.countryCode = jsonNode.get("country_code").asText();
        this.cityName = jsonNode.get("city_name").asText();
        this.dateTime = jsonNode.get("ob_time").asText();
        this.temp = jsonNode.get("temp").asDouble();
        this.rh = jsonNode.get("rh").asDouble();
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

    public void setDateTime(String datetime) {
        this.dateTime = datetime;
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

    public static class Builder {
        private Long id;
        private String description;
        private String cityName;
        private String dateTime;
        private String countryCode;
        private Double temp;
        private Double rh;
        private List<WeatherHistory> weatherHistoryList;
        private List<User> userList;

        public Builder() {
            // По умолчанию все поля равны null
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder cityName(String cityName) {
            this.cityName = cityName;
            return this;
        }

        public Builder dateTime(String dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Builder countryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public Builder temp(Double temp) {
            this.temp = temp;
            return this;
        }

        public Builder rh(Double rh) {
            this.rh = rh;
            return this;
        }

        public Builder weatherHistoryList(List<WeatherHistory> weatherHistoryList) {
            this.weatherHistoryList = weatherHistoryList;
            return this;
        }

        public Builder userList(List<User> userList) {
            this.userList = userList;
            return this;
        }

        public Weather build() {
            return new Weather(this);
        }

        public Builder fromExistingWeather(Weather existingWeather) {
            this.id = existingWeather.id;
            this.description = existingWeather.description;
            this.cityName = existingWeather.cityName;
            this.dateTime = existingWeather.dateTime;
            this.countryCode = existingWeather.countryCode;
            this.temp = existingWeather.temp;
            this.rh = existingWeather.rh;
            this.weatherHistoryList = existingWeather.weatherHistoryList;
            this.userList = existingWeather.userList;
            return this;
        }
    }

    private Weather(Builder builder) {
        this.id = builder.id;
        this.description = builder.description;
        this.cityName = builder.cityName;
        this.dateTime = builder.dateTime;
        this.countryCode = builder.countryCode;
        this.temp = builder.temp;
        this.rh = builder.rh;
        this.weatherHistoryList = builder.weatherHistoryList;
        this.userList = builder.userList;
    }
}
