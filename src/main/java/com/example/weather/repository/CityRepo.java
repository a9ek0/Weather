package com.example.weather.repository;

import com.example.weather.entity.City;
import org.springframework.data.repository.CrudRepository;

public interface CityRepo extends CrudRepository<City, Long> {
}
