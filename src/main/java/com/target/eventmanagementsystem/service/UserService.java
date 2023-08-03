//package com.target.eventmanagementsystem.service;
//
//import com.target.eventmanagementsystem.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//
//    public List<Users> listAall(){
//        return userRepository.findAll();
//    }
//
//    public void save(Users users){
//        userRepository.save(users);
//    }
//
//    public Users get(Integer id){
//        return userRepository.findById(id).get();
//    }
//
//    public void delete(Integer id){
//        userRepository.deleteById(id);
//    }
//
//}

package com.target.eventmanagementsystem.service;

import com.target.eventmanagementsystem.exceptions.BadRequestException;
import com.target.eventmanagementsystem.exceptions.NotFoundException;
import com.target.eventmanagementsystem.models.User;
import com.target.eventmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + id));
    }

    public User createUser(User user) {
        if (user.getFirstName() == null || user.getLastName().isEmpty() ||
                user.getEmail() == null || user.getPassword().isEmpty()) {
            throw new BadRequestException("Invalid user data. Name, email, password must be provided.");
        }

        return userRepository.save(user);
    }

    public User updateUser(User user) {
        Long userId = user.getId();
        if (userId == null || !userRepository.existsById(userId)) {
            throw new NotFoundException("User not found with ID: " + userId);
        }

        User existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser == null) {
            throw new NotFoundException("User not found with ID: " + userId);
        }

        if (user.getFirstName() == null || user.getFirstName().isEmpty() ||
                user.getLastName() == null || user.getLastName().isEmpty() ||
                user.getEmail() == null || user.getEmail().isEmpty() ||
                user.getPassword() == null || user.getPassword().isEmpty() ) {
            throw new BadRequestException("Invalid user data. Name, email and password must be provided.");
        }

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
            throw new NotFoundException("User not found with ID: " + id);
        }

        userRepository.deleteById(id);
    }

}
