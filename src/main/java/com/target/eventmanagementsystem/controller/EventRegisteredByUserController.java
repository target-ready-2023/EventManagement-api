package com.target.eventmanagementsystem.controller;


import com.target.eventmanagementsystem.models.Event;
import com.target.eventmanagementsystem.payloads.ApiResponse;
import com.target.eventmanagementsystem.service.EventRegisteredByUserListService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class EventRegisteredByUserController {

    private final EventRegisteredByUserListService eventRegisteredByUserListService;

    @Autowired
    public EventRegisteredByUserController(EventRegisteredByUserListService eventRegisteredByUserListService){
        this.eventRegisteredByUserListService = eventRegisteredByUserListService;
    }

    @GetMapping("/user/{userId}/registered-events")
    public ResponseEntity<ApiResponse<List<Event>>> getEventRegisteredByUser(@PathVariable Integer userId){
        List<Event> registeredEvents = eventRegisteredByUserListService.getEventsRegisteredByUser(userId);
        return ResponseEntity.ok(new ApiResponse<>(registeredEvents,"Events registered by user retrieved successfully"));
    }
}
