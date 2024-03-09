package com.example.weather.repository;

import com.example.weather.entity.Weather;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WeatherRepo extends CrudRepository<Weather, Long> {

    List<Weather> findByCityName(String cityName);

    @Query("SELECT w FROM Weather w WHERE w.city.name = :cityName")
    List<Weather> findWeatherByCityName(@Param("cityName") String cityName);

    List<Weather> findByCountryCode(String countryCode);
}
