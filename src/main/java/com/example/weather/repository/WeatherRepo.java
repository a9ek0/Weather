package com.example.weather.repository;

import com.example.weather.entity.WeatherEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WeatherRepo extends CrudRepository<WeatherEntity, Long> {

    List<WeatherEntity> findByCityName(String cityName);

}
