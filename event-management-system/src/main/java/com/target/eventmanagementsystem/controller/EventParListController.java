package com.target.eventmanagementsystem.controller;

import com.target.eventmanagementsystem.models.Users;
import com.target.eventmanagementsystem.service.EventParListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/eventparlist")
public class EventParListController {

    @Autowired
    private EventParListService eventParListService;

    @GetMapping("/event/{eventId}/registered-users")
    public ResponseEntity<List<Users>> getUserRegisteredForEvent(@PathVariable Integer eventId){

       try{
           List<Users> registeredUsers = eventParListService.getUserRegisteredForEvent(eventId);
           return new ResponseEntity<>(registeredUsers,HttpStatus.OK);
       }catch(NoSuchElementException e){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }
}
