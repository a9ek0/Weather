package com.example.weather.dto;

import com.example.weather.entity.City;

import java.util.ArrayList;
import java.util.List;

public class CityDTO {
    private String name;
    private List<WeatherBasicDTO> weatherBasicDTOS = new ArrayList<>();


    public CityDTO() {
        // No initialization logic needed for this constructor
    }

    public static CityDTO toModel(City city) {
        CityDTO model = new CityDTO();

        model.setName(city.getName());
        if(city.getWeatherList() != null)
            model.setWeatherBasicDTOS(city.getWeatherList().stream().map(WeatherBasicDTO::toModel).toList());

        return model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WeatherBasicDTO> getWeatherBasicDTOS() {
        return weatherBasicDTOS;
    }

    public void setWeatherBasicDTOS(List<WeatherBasicDTO> weatherBasicDTOS) {
        this.weatherBasicDTOS = weatherBasicDTOS;
    }
}
