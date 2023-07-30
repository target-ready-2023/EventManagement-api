//package com.target.eventmanagementsystem.controller;
//
//import com.target.eventmanagementsystem.models.Event;
//import com.target.eventmanagementsystem.payloads.ApiResponse;
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
//    public ResponseEntity<ApiResponse<Object>> list(){
//        try
//        {
//            List<Event> responseData = eventService.listall();
//            ApiResponse<Object> response = new ApiResponse<>(responseData,"Success");
//            return ResponseEntity.ok(response);
//        }catch (Exception e) {
//            ApiResponse<Event> response = new ApiResponse<>("An error occurred while fetching events data.");
//            return (ResponseEntity<ApiResponse<Object>>) ResponseEntity.internalServerError();
//        }
//
//    }
//
//    @PostMapping("/addEvents")
//    public ResponseEntity<ApiResponse<Object>> add(@RequestBody Event events){
//
////
//        try {
//            // Simulated logic to retrieve student data based on the provided ID
//            Event event = studentService.getStudentById(id);
//
//            if (student != null) {
//                ApiResponse<Student> response = new ApiResponse<>();
//                response.setData(student);
//                return ResponseEntity.ok(response);
//            } else {
//                ApiResponse<Student> response = new ApiResponse<>();
//                response.setError("Student with ID " + id + " not found.");
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//            }
//        } catch (Exception e) {
//            ApiResponse<Student> response = new ApiResponse<>();
//            response.setError("An error occurred while fetching student data.");
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }
//    }
////        eventService.save(events);
////        return  "New Event Added";
//    }
//
//    @GetMapping("/event/{id}")
//    public ResponseEntity<Object> get(@PathVariable Integer id){
//
//        try {
//            Event event =  eventService.get(id);
//
//            if (event != null) {
//                ApiResponse<Event> response = new ApiResponse<>(event,"Success");
////                response.setData(event);
//                return ResponseEntity.ok(response);
//            } else {
//                ApiResponse<Event> response = new ApiResponse<>(event,"Student with ID " + id + " not found.");
////                response.setError("Student with ID " + id + " not found.");
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//            }
//        } catch (Exception e) {
//            ApiResponse<Event> response = new ApiResponse<>("An error occurred while fetching student data.");
////            response.setError("An error occurred while fetching student data.");
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }
//    }
//
//    }
//
//    @PutMapping("/event/{id}")
//    public ResponseEntity<Event> update(@RequestBody Event events,@PathVariable Integer id){
//
//        try{
//            Event existingEvent = eventService.get(id);
//            existingEvent.setTitle(existingEvent.getTitle());
//            existingEvent.setEvent_type(existingEvent.getEvent_type());
//            existingEvent.setDescription(existingEvent.getDescription());
//            existingEvent.setStart_date(existingEvent.getStart_date());
//            existingEvent.setEnd_date(existingEvent.getEnd_date());
//            existingEvent.setRegister_last_date(existingEvent.getRegister_last_date());
//            eventService.save(existingEvent);
//            return new ResponseEntity<Event>(HttpStatus.OK);
//        }catch (NoSuchElementException e){
//            return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
//        }
//
//    }
//
//    @DeleteMapping("/event/{id}")
//    public ResponseEntity<ApiResponse<Object>> delete(@PathVariable Long id) {
//        try {
//
////            boolean deleted = eventService.delete(id);
//            eventService.delete(id);
//            ApiResponse<Object> response = new ApiResponse<>();
//            response.setStatusCode(HttpStatus.OK.value());
//            response.setStatusMessage("Success");
//            return ResponseEntity.ok(response);
////            ApiError error = new ApiError();
////            error.setCode(HttpStatus.NOT_FOUND.value());
////            error.setMessage("Resource not found");
////
////            ApiResponse<Object> response = new ApiResponse<>();
////            response.setStatusCode(HttpStatus.NOT_FOUND.value());
////            response.setStatusMessage("Error");
////            response.setError(error);
////            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
////            }
//        } catch (Exception e) {
//            ApiError error = new ApiError();
//            error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            error.setMessage("Internal server error");
//
//            ApiResponse<Object> response = new ApiResponse<>();
//            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            response.setStatusMessage("Error");
//            response.setError(error);
//
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
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
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/events")
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse<Event>> createEvent(@RequestBody Event event) {
        ApiResponse<Event> response = eventService.createEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<Event>>> getAllEvents() {
        ApiResponse<List<Event>> response = eventService.getAllEvents();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Event>> getEventById(@PathVariable Long id) {
        ApiResponse<Event> response = eventService.getEventById(id);
        if (response.getData() != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Event>> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        ApiResponse<Event> response = eventService.updateEvent(id, event);
        if (response.getData() != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEvent(@PathVariable Long id) {
        ApiResponse<String> response = eventService.deleteEvent(id);
        if (response.getData() != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
