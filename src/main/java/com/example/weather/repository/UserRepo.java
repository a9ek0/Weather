package com.example.weather.repository;

import com.example.weather.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepo extends CrudRepository<User, Long> {

    List<User> findByCountryCode(String countryCode);


}
