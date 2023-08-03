//package com.target.eventManagementSystem.controller;
//
//
//import com.target.eventManagementSystem.service.Impl.EventService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/eventRegister")
//public class EventRegistrationController {
//
//    private final EventService eventService;
//
//    @Autowired
//    public EventRegistrationController(EventService eventService) {
//        this.eventService = eventService;
//    }
//
//    // Register a participant for an event
//    @PutMapping("/{eventId}/register/{userId}")
//    public ResponseEntity<String> registerParticipant(@PathVariable Long eventId, @PathVariable Long userId) {
//        boolean isRegistered = eventService.registerParticipantForEvent(eventId, userId);
//        if (isRegistered) {
//            return ResponseEntity.status(HttpStatus.CREATED).body("Participant registered successfully.");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Event or Participant not found.");
//        }
//    }
//
//    // Deregister a participant from an event
//    @PutMapping("/{eventId}/deregister/{userId}")
//    public ResponseEntity<String> deregisterParticipant(@PathVariable Long eventId, @PathVariable Long userId) {
//        boolean isDeregistered = eventService.deregisterParticipantFromEvent(eventId, userId);
//        if (isDeregistered) {
//            return ResponseEntity.ok("Participant deregistered successfully.");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Event or Participant not found.");
//        }
//    }
//}

package com.target.eventManagementSystem.controller;

import com.target.eventManagementSystem.entity.User;
import com.target.eventManagementSystem.payloads.ApiResponse;
import com.target.eventManagementSystem.payloads.RegistrationRequest;
import com.target.eventManagementSystem.service.Impl.EventRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registration")
public class EventRegistrationController {
    private final EventRegistrationService eventRegistrationService;

    public EventRegistrationController(EventRegistrationService eventRegistrationService) {
        this.eventRegistrationService = eventRegistrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> registerUserForEvent(@RequestBody RegistrationRequest registrationRequest) {
        eventRegistrationService.registerUserForEvent(registrationRequest.getEventId(), registrationRequest.getUserId());
        String message = "User registered for the event successfully.";
        ApiResponse<String> response = new ApiResponse<>(null,message);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/deregister")
    public ResponseEntity<ApiResponse<String>> deregisterUserFromEvent(@RequestBody RegistrationRequest registrationRequest) {
        eventRegistrationService.deregisterUserFromEvent(registrationRequest.getEventId(), registrationRequest.getUserId());
        String message = "User deregistered from the event successfully.";
        ApiResponse<String> response = new ApiResponse<>(null,message);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/registeredUsers")
    public ResponseEntity<ApiResponse<List<User>>> getUsersRegisteredForEvent(@RequestParam Long eventId) {
        List<User> registeredUsers = eventRegistrationService.getUsersRegisteredForEvent(eventId);
        String message = "Users registered for the event retrieved successfully.";
        ApiResponse<List<User>> response = new ApiResponse<>(registeredUsers, message);
        return ResponseEntity.ok(response);
    }
}
