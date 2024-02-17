package com.example.weather.model;

import com.example.weather.entity.WeatherEntity;

public class Weather {
    private Long id;
    private String description;

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    private String city_name;

    private String datetime;
    private double temp;
    private double rh;
    public static Weather toModel(WeatherEntity weatherEntity) {
        Weather model = new Weather();

        model.setId(weatherEntity.getId());
        model.setTemp(weatherEntity.getTemp());
        model.setDescription(weatherEntity.getDescription());
        model.setRh(weatherEntity.getRh());
        model.setCity_name(weatherEntity.getCity_name());
        model.setDatetime(weatherEntity.getDatetime());

        return model;
    }

    public Weather() {
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
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
}
