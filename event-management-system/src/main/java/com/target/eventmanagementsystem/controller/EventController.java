package com.target.eventmanagementsystem.controller;

import com.target.eventmanagementsystem.models.Event;
import com.target.eventmanagementsystem.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/getAllEvents")
    public List<Event> list(){
        return eventService.listAllEvents();
    }

    @PostMapping("/addEvents")
    public String add(@RequestBody Event events){
        eventService.saveEvent(events);
        return  "New Event Added";
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<Event> get(@PathVariable Integer id){
        try{
            Event events = eventService.listEventById(id);
            return new ResponseEntity<Event>(events, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
        }
    }

//    @PutMapping("/event/{id}")
//    public ResponseEntity<Event> update(@RequestBody Event event, @PathVariable Integer id){
//
//        try{
//            Event existingEvent = eventService.listEventById(id);
//            existingEvent.setTitle(existingEvent.getTitle());
//            existingEvent.setEvent_type(existingEvent.getEvent_type());
//            existingEvent.setDescription(existingEvent.getDescription());
//            existingEvent.setStart_date(existingEvent.getStart_date());
//            existingEvent.setEnd_date(existingEvent.getEnd_date());
//            existingEvent.setRegister_last_date(existingEvent.getRegister_last_date());
//            eventService.saveEvent(existingEvent);
//            return new ResponseEntity<Event>(HttpStatus.OK);
//        }catch (NoSuchElementException e){
//            return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
//        }
//
//    }

    @PutMapping("/event/{id}")
    public Event update(@RequestBody Event event){
        return eventService.saveEvent(event);
    }


    @DeleteMapping("/event/{id}")
    public String delete(@PathVariable Integer id){
        try{
            Event events = eventService.listEventById(id);
            eventService.deleteEvent(id);
            return "Event deleted With id " + id;
        }catch(NoSuchElementException e){
            return "No such event exist with id " + id;
        }
    }
}
