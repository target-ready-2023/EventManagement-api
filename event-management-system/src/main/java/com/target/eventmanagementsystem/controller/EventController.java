package com.target.eventmanagementsystem.controller;

import com.target.eventmanagementsystem.models.Event;
import com.target.eventmanagementsystem.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {


    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping()
    public List<Event> getAllEvents(){
        return eventService.getAllEvents();
    }

    @PostMapping("/event")
    public String add(@RequestBody Event events){
        eventService.saveEvent(events);
        return  "New Event Added";
    }

    @GetMapping()
    public ResponseEntity<Event> get(@PathVariable int id){
        return new ResponseEntity<Event>(eventService.getEvent(id), HttpStatus.OK);
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
    public String delete(@PathVariable int id){
        return eventService.deleteEvent(id);
    }
}
