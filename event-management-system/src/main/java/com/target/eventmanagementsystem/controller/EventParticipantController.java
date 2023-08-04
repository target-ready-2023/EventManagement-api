package com.target.eventmanagementsystem.controller;

import com.target.eventmanagementsystem.models.Users;
import com.target.eventmanagementsystem.service.EventParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eventparlist")
public class EventParticipantController {

    private final EventParticipantService eventParticipantService;

    @Autowired
    public EventParticipantController(EventParticipantService eventParticipantService){
        this.eventParticipantService = eventParticipantService;
    }

    @GetMapping("/event/{eventId}/registered-users")
    public ResponseEntity<?> getUsersRegisteredForEvent(@PathVariable Integer eventId){
        List<Users> registeredUsers = eventParticipantService.getUserRegisteredForEvent(eventId);
        if (registeredUsers.isEmpty()){
            return new ResponseEntity<>("No participants in the current event.", HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(registeredUsers,HttpStatus.OK);
        }
    }
}
