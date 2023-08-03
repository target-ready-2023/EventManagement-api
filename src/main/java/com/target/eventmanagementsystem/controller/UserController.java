//package com.target.eventmanagementsystem.controller;
//
//import com.target.eventmanagementsystem.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.NoSuchElementException;
//
//@RestController
//@RequestMapping("/users")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/getAllUsers")
//    public List<Users> list(){
//       return userService.listAall();
//    }
//
//    @PostMapping("/addUser")
//    public String add(@RequestBody Users users){
//        userService.save(users);
//        return "New User Added";
//    }
//
//    @GetMapping("/user/{id}")
//    public ResponseEntity<Users> get(@PathVariable Integer id){
//        try{
//            Users users = userService.get(id);
//            return new ResponseEntity<Users>(users, HttpStatus.OK);
//        }
//        catch (NoSuchElementException e){
//            return new ResponseEntity<Users>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @PutMapping("/user/{id}")
//    public ResponseEntity<Users> update(@RequestBody Users users,@PathVariable Integer id){
//        try{
//            Users existinguser = userService.get(id);
//            existinguser.setFirst_name(existinguser.getFirst_name());
//            existinguser.setLast_name(existinguser.getLast_name());
//            existinguser.setGender(existinguser.getGender());
//            existinguser.setDate(existinguser.getDate());
//            existinguser.setEmail(existinguser.getEmail());
//            existinguser.setRole(existinguser.getRole());
//            existinguser.setPassword(existinguser.getPassword());
//            userService.save(existinguser);
//            return new ResponseEntity<Users>(HttpStatus.OK);
//        }
//        catch (NoSuchElementException e){
//            return new ResponseEntity<Users>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @DeleteMapping("/user/{id}")
//    public String delete(@PathVariable Integer id){
//        try{
//            Users users = userService.get(id);
//            userService.delete(id);
//            return  "Deleted Student with id "+id;
//        }catch (NoSuchElementException e){
//            return "No such user with the given id "+id;
//        }
//    }
//
//}

package com.target.eventmanagementsystem.controller;


import com.target.eventmanagementsystem.models.User;
import com.target.eventmanagementsystem.payloads.ApiResponse;
import com.target.eventmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(new ApiResponse<>(true, "Users retrieved successfully", users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "User retrieved successfully", user));
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "User created successfully", createdUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(new ApiResponse<>(true, "User updated successfully", updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "User deleted successfully", null));
    }
}
