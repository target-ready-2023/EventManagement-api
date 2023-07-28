package com.target.eventmanagementsystem.controller;

import com.target.eventmanagementsystem.models.Events;
import com.target.eventmanagementsystem.payloads.ApiResponse;
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
    private EventService eventService;

    @GetMapping("/getAllEvents")
    public ResponseEntity<Object> list(){

        return ApiResponse.apiResponse("Requested events details",HttpStatus.OK,eventService.listall());

//        return eventService.listall();
    }

    @PostMapping("/addEvents")
    public String add(@RequestBody Events events){
        eventService.save(events);
        return  "New Event Added";
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<Object> get(@PathVariable Integer id){

        return ApiResponse.apiResponse("Requested event details",HttpStatus.OK,eventService.get(id));

//        try{
//            Events events = eventService.get(id);
//            return new ResponseEntity<Events>(events, HttpStatus.OK);
//        }catch (NoSuchElementException e){
//            return new ResponseEntity<Events>(HttpStatus.NOT_FOUND);
//        }
    }

    @PutMapping("/event/{id}")
    public ResponseEntity<Events> update(@RequestBody Events events,@PathVariable Integer id){

        try{
            Events existingEvent = eventService.get(id);
            existingEvent.setTitle(existingEvent.getTitle());
            existingEvent.setEvent_type(existingEvent.getEvent_type());
            existingEvent.setDescription(existingEvent.getDescription());
            existingEvent.setStart_date(existingEvent.getStart_date());
            existingEvent.setEnd_date(existingEvent.getEnd_date());
            existingEvent.setRegister_last_date(existingEvent.getRegister_last_date());
            eventService.save(existingEvent);
            return new ResponseEntity<Events>(HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<Events>(HttpStatus.NOT_FOUND);
        }

    }


    @DeleteMapping("/event/{id}")
    public String delete(@PathVariable Integer id){
        try{
            Events events = eventService.get(id);
            eventService.delete(id);
            return "Event deleted With id "+id;
        }catch(NoSuchElementException e){
            return "No such event exist with id "+id;
        }
    }
}
