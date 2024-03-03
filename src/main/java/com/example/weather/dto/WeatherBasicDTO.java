package com.example.weather.dto;

import com.example.weather.entity.Weather;

import java.util.ArrayList;
import java.util.List;

public class WeatherBasicDTO {
    private String cityName;
    private String datetime;
    private String description;
    private double temp;
    private double rh;
    private String countryCode;
    private List<WeatherHistoryDTO> weatherHistoryDTOList = new ArrayList<>();

    public static WeatherBasicDTO toModel(Weather weather) {
        WeatherBasicDTO model = new WeatherBasicDTO();

        model.setTemp(weather.getTemp());
        model.setDescription(weather.getDescription());
        model.setRh(weather.getRh());
        model.setCityName(weather.getCityName());
        model.setDatetime(weather.getDateTime());
        model.setCountryCode(weather.getCountryCode());
        if(weather.getWeatherHistoryList() != null)
            model.setWeatherHistoryDTOList(weather.getWeatherHistoryList().stream().map(WeatherHistoryDTO::toModel).toList());

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

    public List<WeatherHistoryDTO> getWeatherHistoryDTOList() {
        return weatherHistoryDTOList;
    }

    public void setWeatherHistoryDTOList(List<WeatherHistoryDTO> weatherHistoryDTOList) {
        this.weatherHistoryDTOList = weatherHistoryDTOList;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
