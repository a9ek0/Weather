package com.example.weather.repository;

import com.example.weather.entity.Weather;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Weather entity, providing CRUD operations and custom queries.
 */
@Repository
public interface WeatherRepository extends CrudRepository<Weather, Long> {

  List<Weather> findByCityName(String cityName);

  @Query("SELECT w FROM Weather w WHERE w.city.name = :cityName")
  List<Weather> findWeatherByCityName(@Param("cityName") String cityName);

  List<Weather> findByCountryCode(String countryCode);
}
