package com.example.weather.dto;

import com.example.weather.entity.Weather;

public class WeatherBasicDTO {
    private String dateTime;
    private String description;
    private double temp;
    private double rh;
    private String countryCode;

    public static WeatherBasicDTO toModel(Weather weather) {
        WeatherBasicDTO model = new WeatherBasicDTO();

        model.setTemp(weather.getTemp());
        model.setDescription(weather.getDescription());
        model.setRh(weather.getRh());
        model.setDateTime(weather.getDateTime().toString());
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
    public WeatherBasicDTO() {
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
