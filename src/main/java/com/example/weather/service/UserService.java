package com.example.weather.service;

import com.example.weather.dto.UserBasicDTO;
import com.example.weather.dto.UserDTO;
import com.example.weather.entity.User;
import com.example.weather.exception.UserNotFoundException;
import com.example.weather.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

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

    public User findUserById(Long userId) {
        if (userId != null)
            return userRepo.findById(userId).get();
        return null;
    }

    public List<User> getAllUsers(String countryCode) {
        return userRepo.findByCountryCode(countryCode);
    }

    public Long delete(Long id) {
        userRepo.deleteById(id);
        return id;
    }

    public UserBasicDTO complete(Long id, User updatedUser) {
        User user = userRepo.findById(id).get();

        user.setCountryCode(updatedUser.getCountryCode());
        user.setEmail(updatedUser.getEmail());
        user.setName(updatedUser.getName());
        if(!updatedUser.getWeatherList().isEmpty())
            user.setWeatherList(updatedUser.getWeatherList());

        return UserBasicDTO.toModel(userRepo.save(user));
    }

}
