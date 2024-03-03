package com.example.weather.dto;

import com.example.weather.entity.User;

public class UserBasicDTO {
    private String countryCode;
    private String name;
    private String email;
    public static UserBasicDTO toModel(User user) {
        UserBasicDTO model = new UserBasicDTO();

        model.setCountryCode(user.getCountryCode());
        model.setEmail(user.getEmail());
        model.setName(user.getName());

        return model;
    }

    public UserBasicDTO() {
        // No initialization logic needed for this constructor
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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
