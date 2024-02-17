package com.example.weather.exception;

public class WeatherNotFoundException extends Exception {
    public WeatherNotFoundException(String message) {
        super(message);
    }
}
