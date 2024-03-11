package com.example.weather.repository;

import com.example.weather.entity.City;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for managing City entities.
 * Extends CrudRepository for basic CRUD operations on City entities.
 */
public interface CityRepo extends CrudRepository<City, Long> {
  City findByName(String name);
}
