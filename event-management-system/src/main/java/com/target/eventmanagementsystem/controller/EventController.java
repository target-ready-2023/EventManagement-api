package com.target.eventmanagementsystem.controller;

import com.target.eventmanagementsystem.models.Events;
import com.target.eventmanagementsystem.payloads.ApiError;
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
    public ResponseEntity<ApiResponse<Object>> list(){

        try
        {
            Object responseData = eventService.listall();

            // Create a success response
            ApiResponse<Object> response = new ApiResponse<>();
            response.setStatusCode(HttpStatus.OK.value());
            response.setStatusMessage("Success");
            response.setData(responseData);

            return ResponseEntity.ok(response);
        }catch (Exception e) {
            // Create an error response
            ApiError error = new ApiError();
            error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            error.setMessage("Internal server error");

            ApiResponse<Object> response = new ApiResponse<>();
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Error");
            response.setError(error);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @PostMapping("/addEvents")
    public String add(@RequestBody Events events){
        eventService.save(events);
        return  "New Event Added";
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<Object> get(@PathVariable Integer id){

        try
        {
            Object responseData = eventService.get(id);

            // Create a success response
            ApiResponse<Object> response = new ApiResponse<>();
            response.setStatusCode(HttpStatus.OK.value());
            response.setStatusMessage("Success");
            response.setData(responseData);

            return ResponseEntity.ok(response);
        }catch (Exception e) {
            // Create an error response
            ApiError error = new ApiError();
            error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            error.setMessage("Internal server error");

            ApiResponse<Object> response = new ApiResponse<>();
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Error");
            response.setError(error);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

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
    public ResponseEntity<ApiResponse<Object>> deleteExampleData(@PathVariable Long id) {
        try {

            boolean deleted = eventService.delete(id);

            if (deleted) {
                ApiResponse<Object> response = new ApiResponse<>();
                response.setStatusCode(HttpStatus.OK.value());
                response.setStatusMessage("Success");
                return ResponseEntity.ok(response);
            } else {
                ApiError error = new ApiError();
                error.setCode(HttpStatus.NOT_FOUND.value());
                error.setMessage("Resource not found");

                ApiResponse<Object> response = new ApiResponse<>();
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setStatusMessage("Error");
                response.setError(error);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            ApiError error = new ApiError();
            error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            error.setMessage("Internal server error");

            ApiResponse<Object> response = new ApiResponse<>();
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatusMessage("Error");
            response.setError(error);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
