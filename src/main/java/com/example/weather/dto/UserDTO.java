package com.example.weather.dto;

import com.example.weather.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    private String countryCode;
    private String name;
    private String email;
    private List<WeatherDTO> weatherDTOList = new ArrayList<>();

    public static UserDTO toModel(User user) {
        UserDTO model = new UserDTO();

        model.setCountryCode(user.getCountryCode());
        model.setEmail(user.getEmail());
        model.setName(user.getName());
        if(model.getWeatherDTOList() != null)
            model.setWeatherDTOList(user.getWeatherList().stream().map(WeatherDTO::toModel).toList());

        return model;
    }

    public UserDTO() {
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public List<WeatherDTO> getWeatherDTOList() {
        return weatherDTOList;
    }

    public void setWeatherDTOList(List<WeatherDTO> weatherDTOList) {
        this.weatherDTOList = weatherDTOList;
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

