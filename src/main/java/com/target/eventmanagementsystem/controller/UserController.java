package com.target.eventmanagementsystem.controller;

import com.target.eventmanagementsystem.models.User;
import com.target.eventmanagementsystem.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping()
    public String createUser(@RequestBody User user){
        userService.createUser(user.getId());
        return "New User Added";
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id){
        return new ResponseEntity<User>(userService.getUser(id), HttpStatus.OK);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id){

        userService.getUser(id);
        return new ResponseEntity<User>(HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable Integer id){
        try{
            User user = userService.getUser(id);
            userService.deleteUser(id);
            return  "Deleted Student with id "+id;
        }catch (NoSuchElementException e){
            return "No such user with the given id "+id;
        }
    }

}
