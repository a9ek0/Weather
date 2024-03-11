package com.example.weather.repository;

import com.example.weather.entity.User;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for User entities.
 */
public interface UserRepo extends CrudRepository<User, Long> {

  List<User> findByCountryCode(String countryCode);


}
