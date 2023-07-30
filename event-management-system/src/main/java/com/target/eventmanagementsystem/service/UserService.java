package com.target.eventmanagementsystem.service;

import com.target.eventmanagementsystem.models.User;
import com.target.eventmanagementsystem.payloads.ApiResponse;
import com.target.eventmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ApiResponse<User> createUser(User user) {

        try {
            User newUser = userRepository.save(user);
            return new ApiResponse<>(newUser, "User created successfully.");
        } catch (Exception e) {
            return new ApiResponse<>(null, "Failed to create a new user.");
        }
    }

    public ApiResponse<User> getUserById(Long id) {
        try {
            User user = userRepository.findById(id).orElse(null);

            if (user != null) {
                return new ApiResponse<>(user, "User found.");
            } else {
                return new ApiResponse<>(null, "User not found.");
            }
        } catch (Exception e) {
            return new ApiResponse<>(null, "An error occurred while fetching user data.");
        }
    }

    public ApiResponse<List<User>> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();

            if (!users.isEmpty()) {
                return new ApiResponse<>(users, "Users fetched successfully.");
            } else {
                return new ApiResponse<>(null, "No users found.");
            }
        } catch (Exception e) {
            return new ApiResponse<>(null, "An error occurred while fetching users data.");
        }
    }

    public ApiResponse<User> updateUser(Long id, User user) {
        try {
            Optional<User> optionalUser = userRepository.findById(id);

            if (optionalUser.isPresent()) {
               User existingUser = optionalUser.get();

               existingUser.setFirstName(user.getFirstName());
                existingUser.setLastName(user.getLastName());
                existingUser.setRole(user.getRole());
                existingUser.setEmail(user.getEmail());
                existingUser.setGender(user.getGender());
                existingUser.setPassword(user.getPassword());
                existingUser.setDate(user.getDate());

                User updatedUser = userRepository.save(existingUser);

                return new ApiResponse<>(updatedUser, "User updated successfully.");
            } else {
               return new ApiResponse<>(null, "User not found.");
            }
        } catch (Exception e) {
            return new ApiResponse<>(null, "An error occurred while updating the user data.");
        }
    }

    public ApiResponse<String> deleteUser(Long id) {
        try {
            User existingUser = userRepository.findById(id).orElse(null);

            if (existingUser != null) {
                userRepository.delete(existingUser);
                return new ApiResponse<>("User with ID " + id + " deleted successfully.");
            } else {
                return new ApiResponse<>(null, "User not found.");
            }
        } catch (Exception e) {
            return new ApiResponse<>(null, "An error occurred while deleting the user.");
        }
    }
}
