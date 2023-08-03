package com.target.eventmanagementsystem.service;

import com.target.eventmanagementsystem.exception.UserNotFoundException;
import com.target.eventmanagementsystem.models.User;
import com.target.eventmanagementsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void createUser(int userId){

        try {
            User user = getUser(userId);
            user.setFirstName(user.getFirstName());
            user.setLastName(user.getLastName());
            user.setGender(user.getGender());
            user.setDate(user.getDate());
            user.setEmail(user.getEmail());
            user.setRole(user.getRole());
            user.setPassword(user.getPassword());
            userRepository.save(user);
        }catch (UserNotFoundException e){
            throw new UserNotFoundException("User not found with the ID " + userId);
        }
    }

    public User getUser(int id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
    }

    public void deleteUser(Integer id){
        userRepository.deleteById(id);
    }

}

