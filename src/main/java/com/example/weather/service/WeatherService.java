package com.example.weather.service;

import com.example.weather.entity.WeatherEntity;
import com.example.weather.exception.WeatherNotFoundException;
import com.example.weather.model.Weather;
import com.example.weather.repository.WeatherRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    private final ObjectMapper objectMapper;

    @Autowired private WeatherRepo weatherRepo;

    @Autowired
    public WeatherService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    public WeatherEntity weatherResponse(WeatherEntity weather) {
        return weatherRepo.save(weather);
    }

    public Weather getWeather(Long id) throws WeatherNotFoundException {
        WeatherEntity weather = weatherRepo.findById(id).get();
        if (weather == null) {
            throw new WeatherNotFoundException("Weather not found!");
        }
        return Weather.toModel(weather);
    }

    public Long delete(Long id) {
        weatherRepo.deleteById(id);
        return id;
    }

    public WeatherEntity processJson(String jsonResponse) {
        try {
            return objectMapper.readValue(jsonResponse, WeatherEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
