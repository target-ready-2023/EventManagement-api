package com.target.eventmanagementsystem.controller;
import com.target.eventmanagementsystem.models.Users;
import com.target.eventmanagementsystem.service.EventUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-events")
public class EventUserController {

    private EventUserService eventService;

    @Autowired
    public EventUserController(EventUserService eventService) {
        this.eventService = eventService;
    }

    // Register a participant for an event
    @PostMapping("/{eventId}/register")
    public ResponseEntity<String> registerParticipant(@PathVariable int eventId, @RequestBody Users participant) {
        int isRegistered = eventService.registerParticipantForEvent(eventId, participant.getId());
        if (isRegistered == 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Participant registered successfully.");
        } else if(isRegistered == 1){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You are applying beyond last date of registration. Hence, registration closed!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Event or Participant not found.");
        }
    }

    // Deregister a participant from an event
    @PostMapping("/{eventId}/deregister")
    public ResponseEntity<String> deregisterParticipant(@PathVariable int eventId, @RequestBody Users participant) {
        boolean isDeregistered = eventService.deregisterParticipantFromEvent(eventId, participant.getId());
        if (isDeregistered) {
            return ResponseEntity.ok("Participant deregistered successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Event or Participant not found.");
        }
    }
}