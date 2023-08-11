package com.target.eventmanagementsystem.service;

import com.target.eventmanagementsystem.exceptions.ApiException;
import com.target.eventmanagementsystem.models.Gender;
import com.target.eventmanagementsystem.models.User;
import com.target.eventmanagementsystem.models.UserRoles;
import com.target.eventmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User not found with ID: " + id));
    }

    public User createUser(User user) {
        validateUser(user);
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        Long userId = user.getId();

        if (userId == null ) {
            throw new ApiException(HttpStatus.NOT_FOUND, "User not found with ID: " + userId);
        }

        User existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser == null) {
            new ApiException(HttpStatus.NOT_FOUND, "User not found with ID: " + userId);
        }

        validateUser(user);

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setGender(user.getGender());
        existingUser.setRole(user.getRole());
        existingUser.setDob(user.getDob());

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {

        if (!userRepository.existsById(id)) {
            throw new ApiException(HttpStatus.NOT_FOUND, "User not found with ID: " + id);
        }

        userRepository.deleteById(id);
    }

    public void validateUser(User user)
    {
        if (user.getFirstName() == null || user.getLastName() == null ||
                user.getEmail() == null || user.getPassword() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid user data. Name, email and password must be provided.");
        }

        // DOB should be before today's date
        if (user.getDob() == null || user.getDob().isAfter(LocalDate.now())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid date of birth.");
        }

        // Gender should contain "MALE", "FEMALE" or "OTHER"
        List<Gender> validGender = Arrays.asList(Gender.FEMALE, Gender.MALE, Gender.OTHER);
        if (!validGender.contains(user.getGender())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid user gender.");
        }

        List<UserRoles> validUserRoles = Arrays.asList(UserRoles.STUDENT, UserRoles.ADMIN);
        if (!validUserRoles.contains(user.getRole())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid user role.");
        }

        // Unique user
        if (isDuplicateUser(user)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Duplicate user found.");
        }
    }

    public boolean isDuplicateUser(User newUser) {

        List<User> existingUsers = userRepository.findByEmail(
                newUser.getEmail());

        return existingUsers.stream()
                .anyMatch(existingUser ->
                        newUser.getId() == null ||
                                (!existingUser.getId().equals(newUser.getId()))
                );
    }
}
