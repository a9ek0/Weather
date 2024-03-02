package com.example.weather.repository;

import com.example.weather.entity.WeatherHistory;
import org.springframework.data.repository.CrudRepository;

public interface WeatherHistoryRepo extends CrudRepository<WeatherHistory, Long> {
}
