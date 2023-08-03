package com.target.eventmanagementsystem.controller;

import com.target.eventmanagementsystem.service.Impl.EventRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventRegistrationController {

    private final EventRegistrationService eventRegistrationService;

    public EventRegistrationController(EventRegistrationService eventRegistrationService) {
        this.eventRegistrationService = eventRegistrationService;
    }

    // Register a participant for an event
    @PostMapping("/{eventId}/register/{userId}")
    public ResponseEntity<String> registerParticipant(@PathVariable Long eventId, @RequestBody Long userId) {
        eventRegistrationService.registerParticipantForEvent(eventId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Participant registered successfully.");
    }

    // Deregister a participant from an event
    @PostMapping("/{eventId}/deregister/{userId}")
    public ResponseEntity<String> deregisterParticipant(@PathVariable Long eventId, @RequestBody Long userId) {
        eventRegistrationService.deregisterParticipantFromEvent(eventId, userId);
        return ResponseEntity.ok("Participant deregistered successfully.");
    }
}