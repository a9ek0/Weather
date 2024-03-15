package com.example.weather.repository;

import com.example.weather.entity.City;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing City entities.
 * Extends CrudRepository for basic CRUD operations on City entities.
 */
@Repository
public interface CityRepository extends CrudRepository<City, Long> {
  City findByName(String name);
}
