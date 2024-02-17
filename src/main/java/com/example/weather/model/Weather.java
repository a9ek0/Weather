package com.example.weather.model;

import com.example.weather.entity.WeatherEntity;

public class Weather {
    private Long id;
    private String description;

    private String cityName;

    private String datetime;

    private double temp;
    private double rh;

    public static Weather toModel(WeatherEntity weatherEntity) {
        Weather model = new Weather();

        model.setId(weatherEntity.getId());
        model.setTemp(weatherEntity.getTemp());
        model.setDescription(weatherEntity.getDescription());
        model.setRh(weatherEntity.getRh());
        model.setCityName(weatherEntity.getCityName());
        model.setDatetime(weatherEntity.getDatetime());

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
    public Weather() {
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
