package com.target.eventmanagementsystem.service;

import com.target.eventmanagementsystem.exceptions.ApiException;
import com.target.eventmanagementsystem.models.User;
import com.target.eventmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        validateUser(user);

        Optional<User> existingUserOptional = userRepository.findById(user.getId());
        if (existingUserOptional.isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "User not found with ID: " + user.getId());
        }

        User existingUser = existingUserOptional.get();
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setGender(user.getGender());
        existingUser.setRole(user.getRole());
        existingUser.setDate(user.getDate());

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
        if (user.getId() == null) throw new ApiException(HttpStatus.BAD_REQUEST, "UserId is null");
        if (user.getFirstName() == null || user.getLastName() == null ||
                user.getEmail() == null || user.getPassword() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid user data. Name, email and password must be provided.");
        }
    }

}
