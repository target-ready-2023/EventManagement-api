package com.target.eventmanagementsystem.service;

import com.target.eventmanagementsystem.exceptions.ApiException;
import com.target.eventmanagementsystem.models.Gender;
import com.target.eventmanagementsystem.models.User;
import com.target.eventmanagementsystem.models.UserRoles;
import com.target.eventmanagementsystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(users, result);
    }

    @Test
    void testGetUserById() {
        Long userId = 1L;
        User user = new User(userId, "John", "Doe", LocalDate.of(1990, 5, 15),
                Gender.MALE, "john@example.com", "password", UserRoles.STUDENT);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getUserById(userId);

        assertEquals(user, result);
    }

    @Test
    void testCreateUser() {
        User user = new User(null, "John", "Doe", LocalDate.of(1990, 5, 15),
                Gender.MALE, "john@example.com", "password", UserRoles.STUDENT);
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);

        assertEquals(user, result);
    }

    @Test
    void testUpdateUser() {
        Long userId = 1L;
        User existingUser = new User(userId, "Old First Name", "Old Last Name",
                LocalDate.of(1990, 5, 15), Gender.MALE, "old@example.com", "password", UserRoles.STUDENT);
        User updatedUser = new User(userId, "Updated First Name", "Updated Last Name",
                LocalDate.of(1992, 3, 20), Gender.FEMALE, "updated@example.com", "password@11", UserRoles.ADMIN);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(updatedUser);

        User result = userService.updateUser(updatedUser);

        assertEquals(updatedUser, result);
        assertEquals(updatedUser.getFirstName(), result.getFirstName());
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        assertDoesNotThrow(() -> userService.deleteUser(userId));
    }

    @Test
    void testCreateUser_InvalidData() {
        User invalidUser = new User();
        assertThrows(ApiException.class, () -> userService.createUser(invalidUser));
    }

    @Test
    void testCreateUser_InvalidDob() {
        User user = new User(null, "Invalid Dob User", "Doe",
                LocalDate.now().plusDays(1), Gender.MALE, "invalid@example.com", "password", UserRoles.STUDENT);
        assertThrows(ApiException.class, () -> userService.createUser(user));
    }

    @Test
    void testUpdateUser_InvalidId() {
        User invalidUser = new User();
        assertThrows(ApiException.class, () -> userService.updateUser(invalidUser));
    }

    @Test
    void testUpdateUser_UserNotFound() {
        User user = new User(1L, "User Not Found", "Doe", LocalDate.of(1990, 5, 15),
                Gender.MALE, "usernotfound@example.com", "password", UserRoles.STUDENT);

        when(userRepository.findById(1L)).thenThrow(new ApiException(HttpStatus.NOT_FOUND, "User not found with ID: " + 1L));

        assertThrows(ApiException.class, () -> userService.updateUser(user));
    }

    @Test
    void testCreateUser_DuplicateUser() {
        User existingUser = new User(1L, "Existing", "User",
                LocalDate.of(1990, 5, 15), Gender.MALE, "existing@example.com", "password", UserRoles.STUDENT);
        User newUser = new User(null, "New", "User",
                LocalDate.of(1995, 8, 25), Gender.FEMALE, "existing@example.com", "newpassword", UserRoles.ADMIN);

        when(userRepository.findByEmail(newUser.getEmail())).thenReturn(Collections.singletonList(existingUser));

        assertThrows(ApiException.class, () -> userService.createUser(newUser));
    }

    @Test
    void testUpdateUser_InvalidDob() {
        Long userId = 1L;
        User existingUser = new User(userId, "Old First Name", "Old Last Name",
                LocalDate.of(1990, 5, 15), Gender.MALE, "old@example.com", "oldpassword", UserRoles.STUDENT);
        User updatedUser = new User(userId, "Updated First Name", "Updated Last Name",
                LocalDate.now().plusDays(1), Gender.FEMALE, "updated@example.com", "updatedpassword", UserRoles.ADMIN);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        assertThrows(ApiException.class, () -> userService.updateUser(updatedUser));
    }

    @Test
    void testDeleteUser_UserNotFound() {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(false);

        assertThrows(ApiException.class, () -> userService.deleteUser(userId));
    }

}
