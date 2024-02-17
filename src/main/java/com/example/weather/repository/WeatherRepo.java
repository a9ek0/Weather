package com.example.weather.repository;

import com.example.weather.entity.WeatherEntity;
import org.springframework.data.repository.CrudRepository;

public interface WeatherRepo extends CrudRepository<WeatherEntity, Long> {
}
