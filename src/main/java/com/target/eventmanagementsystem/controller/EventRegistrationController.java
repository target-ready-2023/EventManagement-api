package com.target.eventmanagementsystem.controller;

import com.target.eventmanagementsystem.models.Event;
import com.target.eventmanagementsystem.models.Registration;
import com.target.eventmanagementsystem.models.User;
import com.target.eventmanagementsystem.payloads.ApiResponse;
import com.target.eventmanagementsystem.service.EventRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/registration")
@CrossOrigin("http://localhost:3000")
public class EventRegistrationController {

    private final EventRegistrationService eventRegistrationService;

    public EventRegistrationController(EventRegistrationService eventRegistrationService) {
        this.eventRegistrationService = eventRegistrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> registerUserForEvent(@RequestBody Registration registration) {
        eventRegistrationService.registerUserForEvent(registration.getEventId(), registration.getUserId());
        ApiResponse<String> response = new ApiResponse<>(null,"User registered for the event successfully.");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deregister")
    public ResponseEntity<ApiResponse<String>> deregisterUserFromEvent(@RequestBody Registration registration) {
        eventRegistrationService.deregisterUserFromEvent(registration.getEventId(), registration.getUserId());
        ApiResponse<String> response = new ApiResponse<>(null, "User deregistered for the event successfully.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/eventsForUser/{userId}")
    public ResponseEntity<ApiResponse<List<Event>>> getEventsForUser(@PathVariable Long userId) {
        List<Event> events = eventRegistrationService.getAllEventsForUser(userId);
        String message = "Events registered by the user retrieved successfully.";
        ApiResponse<List<Event>> response = new ApiResponse<>(events, message);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/usersForEvent/{eventId}")
    public ResponseEntity<ApiResponse<List<User>>> getUsersForEvent(@PathVariable Long eventId) {
        List<User> users = eventRegistrationService.getAllUsersForEvent(eventId);
        String message = "Users registered for the event retrieved successfully.";
        ApiResponse<List<User>> response = new ApiResponse<>(users, message);
        return ResponseEntity.ok(response);
    }

}