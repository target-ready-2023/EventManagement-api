package com.target.eventmanagementsystem.service;

import com.target.eventmanagementsystem.models.Users;
import com.target.eventmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public List<Users> listAall(){
        return userRepository.findAll();
    }

    public void save(Users users){
        userRepository.save(users);
    }

    public Users get(Integer id){
        return userRepository.findById(id).get();
    }

    public void delete(Integer id){
        userRepository.deleteById(id);
    }

}
