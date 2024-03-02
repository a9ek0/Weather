package com.example.weather.repository;

import com.example.weather.entity.User;
import com.example.weather.entity.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {
}
