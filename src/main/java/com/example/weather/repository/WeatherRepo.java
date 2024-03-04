package com.example.weather.repository;

import com.example.weather.entity.Weather;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WeatherRepo extends CrudRepository<Weather, Long> {

    List<Weather> findByCityName(String cityName);
    List<Weather> findByCountryCode(String countryCode);
}
