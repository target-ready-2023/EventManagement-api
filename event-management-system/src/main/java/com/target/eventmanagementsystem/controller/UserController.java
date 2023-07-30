//package com.target.eventmanagementsystem.controller;
//
//import com.target.eventmanagementsystem.models.Users;
//import com.target.eventmanagementsystem.payloads.ApiResponse;
//import com.target.eventmanagementsystem.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/users")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/getAllUsers")
//    public ResponseEntity<Object> list(){
//
//        try
//        {
//            Object responseData = userService.listAall();
//
//            // Create a success response
//            ApiResponse<Object> response = new ApiResponse<>();
//            response.setStatusCode(HttpStatus.OK.value());
//            response.setStatusMessage("Success");
//            response.setData(responseData);
//
//            return ResponseEntity.ok(response);
//        }catch (Exception e) {
//            // Create an error response
//            ApiError error = new ApiError();
//            error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            error.setMessage("Internal server error");
//
//            ApiResponse<Object> response = new ApiResponse<>();
//            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            response.setStatusMessage("Error");
//            response.setError(error);
//
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }
//    }
//
//    @PostMapping("/addUser")
//    public ResponseEntity<ApiResponse<Object>> add(@RequestBody Users users){
//        try
//        {
//            Object responseData = userService.save(users);
//
//            // Create a success response
//            ApiResponse<Object> response = new ApiResponse<>();
//            response.setStatusCode(HttpStatus.OK.value());
//            response.setStatusMessage("Success");
//            response.setData(responseData);
//
//            return ResponseEntity.ok(response);
//        }catch (Exception e) {
//            // Create an error response
//            ApiError error = new ApiError();
//            error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            error.setMessage("Internal server error");
//
//            ApiResponse<Object> response = new ApiResponse<>();
//            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            response.setStatusMessage("Error");
//            response.setError(error);
//
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }
//
//    }
//
//    @GetMapping("/user/{id}")
//    public ResponseEntity<Object> get(@PathVariable Integer id){
//
//        try
//        {
//            Object responseData = userService.get(id);
//            // Create a success response
//            ApiResponse<Object> response = new ApiResponse<>();
//            response.setStatusCode(HttpStatus.OK.value());
//            response.setStatusMessage("Success");
//            response.setData(responseData);
//
//            return ResponseEntity.ok(response);
//        }catch (Exception e) {
//            // Create an error response
//            ApiError error = new ApiError();
//            error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            error.setMessage("Internal server error");
//
//            ApiResponse<Object> response = new ApiResponse<>();
//            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            response.setStatusMessage("Error");
//            response.setError(error);
//
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }
//
//    }
//
//    @PutMapping("/user/{id}")
//    public ResponseEntity<ApiResponse<Object>> update(@RequestBody Users users,@PathVariable Long id){
////        try{
////            Users existinguser = userService.get(id);
////            existinguser.setFirst_name(existinguser.getFirst_name());
////            existinguser.setLast_name(existinguser.getLast_name());
////            existinguser.setGender(existinguser.getGender());
////            existinguser.setDate(existinguser.getDate());
////            existinguser.setEmail(existinguser.getEmail());
////            existinguser.setRole(existinguser.getRole());
////            existinguser.setPassword(existinguser.getPassword());
////            userService.save(existinguser);
////            return new ResponseEntity<Users>(HttpStatus.OK);
////        }
////        catch (NoSuchElementException e){
////            return new ResponseEntity<Users>(HttpStatus.NOT_FOUND);
////        }
////        UserDto updatedUserDto = this.userService.updateUser(userDto,username);
////        return ResponseEntity.ok(updatedUserDto);
//        try {
//            // Simulated logic to update existing data based on the provided request data and ID
//            Object updatedData = update(users,id);
//
//            if (updatedData != null) {
//                ApiResponse<Object> response = new ApiResponse<>();
//                response.setStatusCode(HttpStatus.OK.value());
//                response.setStatusMessage("Success");
//                response.setData(updatedData);
//                return ResponseEntity.ok(response);
//            } else {
//                ApiError error = new ApiError();
//                error.setCode(HttpStatus.NOT_FOUND.value());
//                error.setMessage("Resource not found");
//
//                ApiResponse<Object> response = new ApiResponse<>();
//                response.setStatusCode(HttpStatus.NOT_FOUND.value());
//                response.setStatusMessage("Error");
//                response.setError(error);
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//            }
//        } catch (Exception e) {
//            ApiError error = new ApiError();
//            error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            error.setMessage("Internal server error");
//
//            ApiResponse<Object> response = new ApiResponse<>();
//            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            response.setStatusMessage("Error");
//            response.setError(error);
//
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }
//    }
//
//    @DeleteMapping("/user/{id}")
//    public ResponseEntity<Object> delete(@PathVariable("id") Long id){
//
//        try {
//            // Simulated logic to delete data based on the provided ID
//            boolean deleted = userService.delete(id);
//
//            if (deleted) {
//                ApiResponse<Object> response = new ApiResponse<>();
//                response.setStatusCode(HttpStatus.OK.value());
//                response.setStatusMessage("Success");
//                return ResponseEntity.ok(response);
//            } else {
//                ApiError error = new ApiError();
//                error.setCode(HttpStatus.NOT_FOUND.value());
//                error.setMessage("Resource not found");
//
//                ApiResponse<Object> response = new ApiResponse<>();
//                response.setStatusCode(HttpStatus.NOT_FOUND.value());
//                response.setStatusMessage("Error");
//                response.setError(error);
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//            }
//        } catch (Exception e) {
//            ApiError error = new ApiError();
//            error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            error.setMessage("Internal server error");
//
//            ApiResponse<Object> response = new ApiResponse<>();
//            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            response.setStatusMessage("Error");
//            response.setError(error);
//
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
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

    @PostMapping("/")
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody User user) {
        ApiResponse<User> response = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        ApiResponse<List<User>> response = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getEventById(@PathVariable Long id) {
        ApiResponse<User> response = userService.getUserById(id);
        if (response.getData() != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateEvent(@PathVariable Long id, @RequestBody User user) {
        ApiResponse<User> response = userService.updateUser(id, user);
        if (response.getData() != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Long id) {
        ApiResponse<String> response = userService.deleteUser(id);
        if (response.getData() != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}

