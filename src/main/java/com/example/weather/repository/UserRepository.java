package com.example.weather.repository;

import com.example.weather.entity.User;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for User entities.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

  List<User> findByCountryCode(String countryCode);


}
