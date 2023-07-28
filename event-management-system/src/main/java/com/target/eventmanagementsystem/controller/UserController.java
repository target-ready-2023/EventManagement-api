package com.target.eventmanagementsystem.controller;

import com.target.eventmanagementsystem.models.Users;
import com.target.eventmanagementsystem.payloads.ApiResponse;
import com.target.eventmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAllUsers")
    public ResponseEntity<Object> list(){

        return ApiResponse.apiResponse("Requested events details",HttpStatus.OK,userService.listAall());
//       return userService.listAall();
    }

    @PostMapping("/addUser")
    public String add(@RequestBody Users users){
        userService.save(users);
        return "New User Added";
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Object> get(@PathVariable Integer id){

        return ApiResponse.apiResponse("Requested events details",HttpStatus.OK,userService.get(id));

//        try{
//            Users users = userService.get(id);
//            return new ResponseEntity<Users>(users, HttpStatus.OK);
//        }
//        catch (NoSuchElementException e){
//            return new ResponseEntity<Users>(HttpStatus.NOT_FOUND);
//        }
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Users> update(@RequestBody Users users,@PathVariable Integer id){
        try{
            Users existinguser = userService.get(id);
            existinguser.setFirst_name(existinguser.getFirst_name());
            existinguser.setLast_name(existinguser.getLast_name());
            existinguser.setGender(existinguser.getGender());
            existinguser.setDate(existinguser.getDate());
            existinguser.setEmail(existinguser.getEmail());
            existinguser.setRole(existinguser.getRole());
            existinguser.setPassword(existinguser.getPassword());
            userService.save(existinguser);
            return new ResponseEntity<Users>(HttpStatus.OK);
        }
        catch (NoSuchElementException e){
            return new ResponseEntity<Users>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/{id}")
    public String delete(@PathVariable Integer id){
        try{
            Users users = userService.get(id);
            userService.delete(id);
            return  "Deleted Student with id "+id;
        }catch (NoSuchElementException e){
            return "No such user with the given id "+id;
        }
    }

}
