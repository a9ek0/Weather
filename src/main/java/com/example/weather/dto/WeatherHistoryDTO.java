package com.example.weather.dto;

import com.example.weather.entity.WeatherHistory;

public class WeatherHistoryDTO {

    private String datetime;
    private String description;
    private double temp;
    private double rh;
    private String countryCode;
    public static WeatherHistoryDTO toModel(WeatherHistory weatherHistory) {
        WeatherHistoryDTO model = new WeatherHistoryDTO();
        model.setTemp(weatherHistory.getTemp());
        model.setDescription(weatherHistory.getDescription());
        model.setRh(weatherHistory.getRh());
        model.setDatetime(weatherHistory.getDatetime());
        model.setCountryCode(weatherHistory.getCountryCode());

        return model;
    }

    public WeatherHistoryDTO() {
        // No initialization logic needed for this constructor
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
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
