package com.example.weather.dto;

import com.example.weather.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    private String countryCode;
    private String name;
    private String email;
    private List<WeatherBasicDTO> weatherBasicDTOList = new ArrayList<>();

    public static UserDTO toModel(User user) {
        UserDTO model = new UserDTO();

        model.setCountryCode(user.getCountryCode());
        model.setEmail(user.getEmail());
        model.setName(user.getName());
        if(model.getWeatherBasicDTOList() != null)
            model.setWeatherBasicDTOList(user.getWeatherList().stream().map(WeatherBasicDTO::toModel).toList());

        return model;
    }

    public UserDTO() {
        // No initialization logic needed for this constructor
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public List<WeatherBasicDTO> getWeatherBasicDTOList() {
        return weatherBasicDTOList;
    }

    public void setWeatherBasicDTOList(List<WeatherBasicDTO> weatherBasicDTOList) {
        this.weatherBasicDTOList = weatherBasicDTOList;
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

