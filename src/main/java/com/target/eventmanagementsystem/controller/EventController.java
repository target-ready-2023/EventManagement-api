//package com.target.eventmanagementsystem.controller;
//
//import com.target.eventmanagementsystem.models.Events;
//import com.target.eventmanagementsystem.service.EventService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.NoSuchElementException;
//
//@RestController
//@RequestMapping("/events")
//public class EventController {
//
//    @Autowired
//    private EventService eventService;
//
//    @GetMapping("/getAllEvents")
//    public List<Events> list(){
//        return eventService.listall();
//    }
//
//    @PostMapping("/addEvents")
//    public String add(@RequestBody Events events){
//        eventService.save(events);
//        return  "New Event Added";
//    }
//
//    @GetMapping("/event/{id}")
//    public ResponseEntity<Events> get(@PathVariable Integer id){
//        try{
//            Events events = eventService.get(id);
//            return new ResponseEntity<Events>(events, HttpStatus.OK);
//        }catch (NoSuchElementException e){
//            return new ResponseEntity<Events>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @PutMapping("/event/{id}")
//    public ResponseEntity<Events> update(@RequestBody Events events,@PathVariable Integer id){
//
//        try{
//            Events existingEvent = eventService.get(id);
//            existingEvent.setTitle(existingEvent.getTitle());
//            existingEvent.setEvent_type(existingEvent.getEvent_type());
//            existingEvent.setDescription(existingEvent.getDescription());
//            existingEvent.setStart_date(existingEvent.getStart_date());
//            existingEvent.setEnd_date(existingEvent.getEnd_date());
//            existingEvent.setRegister_last_date(existingEvent.getRegister_last_date());
//            eventService.save(existingEvent);
//            return new ResponseEntity<Events>(HttpStatus.OK);
//        }catch (NoSuchElementException e){
//            return new ResponseEntity<Events>(HttpStatus.NOT_FOUND);
//        }
//
//    }
//
//
//    @DeleteMapping("/event/{id}")
//    public String delete(@PathVariable Integer id){
//        try{
//            Events events = eventService.get(id);
//            eventService.delete(id);
//            return "Event deleted With id "+id;
//        }catch(NoSuchElementException e){
//            return "No such event exist with id "+id;
//        }
//    }
//}

package com.target.eventmanagementsystem.controller;

import com.target.eventmanagementsystem.models.Event;
import com.target.eventmanagementsystem.payloads.ApiResponse;
import com.target.eventmanagementsystem.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("api/events")
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
    public ResponseEntity<ApiResponse<Event>> updateEvent(@PathVariable Long id, @RequestBody Event event) {
//        event.setId(id);
        event.setId(id);
        Event updatedEvent = eventService.updateEvent(event);
        return ResponseEntity.ok(new ApiResponse<>(updatedEvent, "Event updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok(new ApiResponse<>( null,"Event deleted successfully"));
    }
}
