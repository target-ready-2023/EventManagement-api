package com.target.eventmanagementsystem.controller;
import com.target.eventmanagementsystem.models.User;
import com.target.eventmanagementsystem.service.EventUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eventRegister")
public class EventRegistrationController {

    private  EventUserService eventService;

    @Autowired
    public EventRegistrationController(EventUserService eventService) {
        this.eventService = eventService;
    }

    // Register a participant for an event
    @PostMapping("/{eventId}/register/{userId}")
    public ResponseEntity<String> registerParticipant(@PathVariable Long eventId, @RequestBody User participant) {
        boolean isRegistered = eventService.registerParticipantForEvent(eventId, participant.getId());
        if (isRegistered) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Participant registered successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Event or Participant not found.");
        }
    }

    // Deregister a participant from an event
    @PostMapping("/{eventId}/deregister/{userId}")
    public ResponseEntity<String> deregisterParticipant(@PathVariable Long eventId, @RequestBody User participant) {
        boolean isDeregistered = eventService.deregisterParticipantFromEvent(eventId, participant.getId());
        if (isDeregistered) {
            return ResponseEntity.ok("Participant deregistered successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Event or Participant not found.");
        }
    }
}
