package com.example.weather.service;

import com.example.weather.dto.UserBasicDto;
import com.example.weather.dto.UserDto;
import com.example.weather.entity.User;
import com.example.weather.exception.IdNotFoundException;
import com.example.weather.exception.UserNotFoundException;
import com.example.weather.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  @Test
  void testUserResponse() {
    User user = new User();
    when(userRepository.save(user)).thenReturn(user);

    User result = userService.userResponse(user);

    assertNotNull(result);
    assertEquals(user, result);
  }

  @Test
  void testGetUser_ExistingId() throws UserNotFoundException {
    long id = 1L;
    User user = new User();
    when(userRepository.findById(id)).thenReturn(Optional.of(user));

    UserDto result = userService.getUser(id);

    assertNotNull(result);
  }

  @Test
  void testFindUserById_ExistingId() {
    long id = 1L;
    User user = new User();
    when(userRepository.findById(id)).thenReturn(Optional.of(user));

    User result = userService.findUserById(id);

    assertNotNull(result);
  }

  @Test
  void testGetAllUsers() {
    String countryCode = "US";
    List<User> users = new ArrayList<>();
    users.add(new User());
    when(userRepository.findByCountryCode(countryCode)).thenReturn(users);

    List<User> result = userService.getAllUsers(countryCode);

    assertNotNull(result);
    assertFalse(result.isEmpty());
  }

  @Test
  void testDelete_ValidId() throws IdNotFoundException {
    long id = 1L;
    when(userRepository.findById(id)).thenReturn(Optional.empty());

    IdNotFoundException exception = assertThrows(IdNotFoundException.class,
            () -> userService.delete(id));
    assertEquals("user with such id not found", exception.getMessage());

    verify(userRepository, never()).deleteById(id);
  }

  @Test
  void testDelete_InvalidId() throws IdNotFoundException {
    long id = 1L;
    when(userRepository.findById(id)).thenReturn(Optional.of(new User()));

    userService.delete(id);

    verify(userRepository, times(1)).deleteById(id);
  }

  @Test
  void testComplete_ValidId() {
    long id = 1L;
    User existingUser = new User();
    existingUser.setId(id);
    User updatedUser = new User();
    updatedUser.setId(id);
    when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
    when(userRepository.save(any())).thenReturn(updatedUser);

    UserBasicDto result = userService.complete(id, updatedUser);

    assertNotNull(result);
  }
}
