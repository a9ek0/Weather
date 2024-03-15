package com.example.weather.service;

import com.example.weather.dto.UserBasicDto;
import com.example.weather.dto.UserDto;
import com.example.weather.entity.User;
import com.example.weather.exception.IdNotFoundException;
import com.example.weather.exception.UserNotFoundException;
import com.example.weather.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service class for managing User entities.
 */
@Service
public class UserService {

  private final UserRepository userRepository;
  static final String USER_NOT_FOUND = "User not found";

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Saves a user entity.
   *
   * @param user The user entity to be saved.
   * @return The saved user entity.
   */
  public User userResponse(User user) {
    return userRepository.save(user);
  }

  /**
   * Retrieves a user by its ID and converts it to a UserDto.
   *
   * @param id The ID of the user to retrieve.
   * @return The UserDto corresponding to the retrieved user.
   * @throws UserNotFoundException If the user with the specified ID is not found.
   */
  public UserDto getUser(Long id) throws UserNotFoundException {
    Optional<User> optionalUser = userRepository.findById(id);
    User user = optionalUser.orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

    if (user == null) {
      throw new UserNotFoundException("user not found");
    }
    return UserDto.toModel(user);
  }

  /**
   * Finds a user by its ID.
   *
   * @param userId The ID of the user to find.
   * @return The found user entity or null if not found.
   */
  public User findUserById(Long userId) throws UserNotFoundException {
    if (userId != null) {
      Optional<User> optionalUser = userRepository.findById(userId);
      return optionalUser.orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }
    return null;
  }

  /**
   * Retrieves all users with a specified country code.
   *
   * @param countryCode The country code to filter users by.
   * @return A list of users with the specified country code.
   */
  public List<User> getAllUsers(String countryCode) {
    return userRepository.findByCountryCode(countryCode);
  }

  /**
   * Deletes a user by its ID.
   *
   * @param id The ID of the user to delete.
   * @return The ID of the deleted user.
   * @throws IdNotFoundException If the user with the specified ID is not found.
   */
  public Long delete(Long id) throws IdNotFoundException {
    if (userRepository.findById(id).isEmpty()) {
      throw new IdNotFoundException("user with such id not found");
    }
    userRepository.deleteById(id);
    return id;
  }

  /**
   * Updates a user entity.
   *
   * @param id          The ID of the user to update.
   * @param updatedUser The updated user entity.
   * @return The updated user as a UserBasicDto.
   */
  public UserBasicDto complete(Long id, User updatedUser) throws UserNotFoundException {
    Optional<User> optionalUser = userRepository.findById(id);
    User user = optionalUser.orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

    user.setCountryCode(updatedUser.getCountryCode());
    user.setEmail(updatedUser.getEmail());
    user.setName(updatedUser.getName());
    if (!updatedUser.getWeatherList().isEmpty()) {
      user.setWeatherList(updatedUser.getWeatherList());
    }
    return UserBasicDto.toModel(userRepository.save(user));
  }

}
