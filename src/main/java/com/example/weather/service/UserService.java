package com.example.weather.service;

import com.example.weather.dto.UserDTO;
import com.example.weather.entity.User;
import com.example.weather.entity.Weather;
import com.example.weather.exception.UserNotFoundException;
import com.example.weather.repository.UserRepo;
import com.example.weather.repository.WeatherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private WeatherRepo weatherRepo;

    public User userResponse(User user) {
        return userRepo.save(user);
    }

    public UserDTO getUser(Long id) throws UserNotFoundException {
        User user = userRepo.findById(id).get();
        if (user == null) {
            throw new UserNotFoundException("User not found!");
        }
        return UserDTO.toModel(user);
    }

    public UserDTO createUser(User user, String countryCode) {
        Weather weather = weatherRepo.findByCityName(countryCode);
        user.getWeatherList().add(weather);
        return UserDTO.toModel(userRepo.save(user));
    }

    public User findUserById(Long userId) {
        if(userId != null)
            return userRepo.findById(userId).get();
        return null;
    }



}
