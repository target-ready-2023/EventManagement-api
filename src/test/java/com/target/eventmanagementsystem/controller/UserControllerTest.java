package com.target.eventmanagementsystem.controller;

import com.target.eventmanagementsystem.models.User;
import com.target.eventmanagementsystem.payloads.ApiResponse;
import com.target.eventmanagementsystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.target.eventmanagementsystem.models.Gender.FEMALE;
import static com.target.eventmanagementsystem.models.Gender.MALE;
import static com.target.eventmanagementsystem.models.UserRoles.STUDENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void testGetAllUsers() {
        // Prepare data
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "John", "Doe", LocalDate.of(1990, 5, 15), MALE,
                "john@example.com", "password", STUDENT));

        when(userService.getAllUsers()).thenReturn(users);

        // Call the controller method
        ResponseEntity<ApiResponse<List<User>>> responseEntity = userController.getAllUsers();

        // Verify
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ApiResponse<List<User>> responseBody = responseEntity.getBody();
        assertEquals(users, responseBody.getData());
        assertEquals("Users retrieved successfully", responseBody.getMessage());

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById() {
        // Prepare data
        User user = new User(1L, "Jane", "Smith", LocalDate.of(1985, 8, 20), FEMALE,
                "jane@example.com", "password", STUDENT);

        when(userService.getUserById(1L)).thenReturn(user);

        // Call the controller method
        ResponseEntity<ApiResponse<User>> responseEntity = userController.getUserById(1L);

        // Verify
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ApiResponse<User> responseBody = responseEntity.getBody();
        assertEquals(user, responseBody.getData());
        assertEquals("User retrieved successfully", responseBody.getMessage());

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void testCreateUser() {
        // Prepare data
        User userToCreate = new User(null, "Alice", "Johnson", LocalDate.of(1995, 3, 10), FEMALE,
                "alice@example.com", "password", STUDENT);
        User createdUser = new User(1L, "Alice", "Johnson", LocalDate.of(1995, 3, 10), FEMALE,
                "alice@example.com", "password", STUDENT);

        when(userService.createUser(userToCreate)).thenReturn(createdUser);

        // Call the controller method
        ResponseEntity<ApiResponse<User>> responseEntity = userController.createUser(userToCreate);

        // Verify
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        ApiResponse<User> responseBody = responseEntity.getBody();
        assertEquals(createdUser, responseBody.getData());
        assertEquals("User created successfully", responseBody.getMessage());

        verify(userService, times(1)).createUser(userToCreate);
    }

    @Test
    void testUpdateUser() {
        // Prepare data
        User userToUpdate = new User(1L, "Alice", "Smith", LocalDate.of(1995, 3, 10), FEMALE,
                "alice@example.com", "password", STUDENT);
        User updatedUser = new User(1L, "Alice", "Smith", LocalDate.of(1995, 3, 10), FEMALE,
                "alice@example.com", "password", STUDENT);

        when(userService.updateUser(userToUpdate)).thenReturn(updatedUser);

        // Call the controller method
        ResponseEntity<ApiResponse<User>> responseEntity = userController.updateUser(1L, userToUpdate);

        // Verify
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ApiResponse<User> responseBody = responseEntity.getBody();
        assertEquals(updatedUser, responseBody.getData());
        assertEquals("User updated successfully", responseBody.getMessage());

        verify(userService, times(1)).updateUser(userToUpdate);
    }

    @Test
    void testDeleteUser() {
        // Call the controller method
        ResponseEntity<ApiResponse<Void>> responseEntity = userController.deleteUser(1L);

        // Verify
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ApiResponse<Void> responseBody = responseEntity.getBody();
        assertNull(responseBody.getData());
        assertEquals("User deleted successfully", responseBody.getMessage());

        verify(userService, times(1)).deleteUser(1L);
    }
}
