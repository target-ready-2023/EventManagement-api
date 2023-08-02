package com.target.eventmanagementsystem.controller;
import com.target.eventmanagementsystem.models.User;
import com.target.eventmanagementsystem.service.EventParticipantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventRegistrationController {

    private final EventParticipantService eventService;

    public EventRegistrationController(EventParticipantService eventService) {
        this.eventService = eventService;
    }

    // Register a participant for an event
    @PostMapping("/{eventId}/register/{userId}")
    public ResponseEntity<String> registerParticipant(@PathVariable int eventId, @RequestBody int userId) {
        eventService.registerParticipantForEvent(eventId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Participant registered successfully.");
    }

    // Deregister a participant from an event
    @PostMapping("/{eventId}/deregister/{userId}")
    public ResponseEntity<String> deregisterParticipant(@PathVariable int eventId, @RequestBody int userId) {
        eventService.deregisterParticipantFromEvent(eventId, userId);
        return ResponseEntity.ok("Participant deregistered successfully.");
    }
}