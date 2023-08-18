package com.target.eventmanagementsystem.controller;

import com.target.eventmanagementsystem.models.Event;
import com.target.eventmanagementsystem.payloads.ApiResponse;
import com.target.eventmanagementsystem.service.EventService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/events")
@CrossOrigin("http://localhost:3000")
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<Event>>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(new ApiResponse<>(events, "Events retrieved successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Event>> getEventById(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        return ResponseEntity.ok(new ApiResponse<>(event, "Event retrieved successfully"));
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<Event>> createEvent(@RequestBody Event event) {
        Event createdEvent = eventService.createEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(createdEvent, "Event created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Event>> updateEvent(@PathVariable Long id, @RequestBody @NotNull Event event) {

        event.setId(id);
        Event updatedEvent = eventService.updateEvent(event);
        return ResponseEntity.ok(new ApiResponse<>(updatedEvent, "Event updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok(new ApiResponse<>( null,"Event deleted successfully"));
    }

    @GetMapping("/upcomingEvents")
    public ResponseEntity<ApiResponse<List<Event>>> getUpcomingEvents() {
        List<Event> upcomingEvents = eventService.getUpcomingEvents();
        return ResponseEntity.ok(new ApiResponse<>(upcomingEvents, "Upcoming events retrieved successfully"));
    }

    @GetMapping("/pastEvents")
    public ResponseEntity<ApiResponse<List<Event>>> getPastEvents() {
        List<Event> pastEvents = eventService.getPastEvents();
        return ResponseEntity.ok(new ApiResponse<>(pastEvents, "Past events retrieved successfully"));
    }

    @GetMapping("/ongoingEvents")
    public ResponseEntity<ApiResponse<List<Event>>> getOngoingEvents() {
        List<Event> ongoingEvents = eventService.getOngoingEvents();
        return ResponseEntity.ok(new ApiResponse<>(ongoingEvents, "Ongoing events retrieved successfully"));

    }
}
