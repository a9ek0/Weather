package com.example.weather.dto;

import com.example.weather.entity.City;

import java.util.ArrayList;
import java.util.List;

public class CityDTO {
    private String name;

    private List<WeatherDTO> weatherDTOList = new ArrayList<>();


    public CityDTO() {
    }

    public static CityDTO toModel(City city) {
        CityDTO model = new CityDTO();

        model.setName(city.getName());
        if(city.getWeatherList() != null)
            model.setWeatherDTOList(city.getWeatherList().stream().map(WeatherDTO::toModel).toList());


        return model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WeatherDTO> getWeatherDTOList() {
        return weatherDTOList;
    }

    public void setWeatherDTOList(List<WeatherDTO> weatherDTOList) {
        this.weatherDTOList = weatherDTOList;
    }
}
