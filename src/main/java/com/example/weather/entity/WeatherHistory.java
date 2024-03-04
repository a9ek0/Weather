package com.example.weather.entity;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class WeatherHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    private String description;
    private String countryCode;
    private LocalDateTime dateTime;
    private double temp;
    private double rh;

    @ManyToOne
    @JoinColumn(name = "weatherId")
    private Weather weather;

    public WeatherHistory() {
    }

    public WeatherHistory(JsonNode jsonNode) {
        this.description = jsonNode.get("weather").get("description").asText();
        this.countryCode = jsonNode.get("country_code").asText();

        String pattern = "yyyy-MM-dd HH:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        this.dateTime = LocalDateTime.parse((jsonNode.get("ob_time").asText()), formatter);

        this.temp = jsonNode.get("temp").asDouble();
        this.rh = jsonNode.get("rh").asDouble();
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime datetime) {
        this.dateTime = datetime;
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

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
