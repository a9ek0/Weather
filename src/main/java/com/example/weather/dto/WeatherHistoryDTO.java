package com.example.weather.dto;

import com.example.weather.entity.WeatherHistory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WeatherHistoryDTO {

    private LocalDateTime dateTime;
    private String description;
    private double temp;
    private double rh;
    private String countryCode;
    public static WeatherHistoryDTO toModel(WeatherHistory weatherHistory) {
        WeatherHistoryDTO model = new WeatherHistoryDTO();
        model.setTemp(weatherHistory.getTemp());
        model.setDescription(weatherHistory.getDescription());
        model.setRh(weatherHistory.getRh());
        model.setDateTime(weatherHistory.getDateTime());
        model.setCountryCode(weatherHistory.getCountryCode());

        return model;
    }

    public WeatherHistoryDTO() {
        // No initialization logic needed for this constructor
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
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
