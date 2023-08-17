package com.target.eventmanagementsystem.controller;

import com.target.eventmanagementsystem.models.Event;
import java.time.LocalDate;
import com.target.eventmanagementsystem.payloads.ApiResponse;
import com.target.eventmanagementsystem.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.ArrayList;

import static com.target.eventmanagementsystem.models.EventTypes.SPORTS_DAY;
import static com.target.eventmanagementsystem.models.EventTypes.SCHOOL_DAY;
import static com.target.eventmanagementsystem.models.EventTypes.TALENT_DAY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EventControllerTest {

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEvents() {
        // Data prep
        List<Event> events = new ArrayList<>();
        events.add(new Event(1L, "Event 1", "Running competition",SPORTS_DAY ,
                LocalDate.parse("2023-08-25"), LocalDate.parse("2023-09-26"), LocalDate.parse("2023-08-24")));
        events.add(new Event(2L, "Event 2", "Singing competition",SCHOOL_DAY ,
                LocalDate.parse("2023-08-25"), LocalDate.parse("2023-09-26"), LocalDate.parse("2023-08-24")));

        // Mock the service method
        when(eventService.getAllEvents()).thenReturn(events);

        //calling controller method
        ResponseEntity<ApiResponse<List<Event>>> response = eventController.getAllEvents();


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(events, response.getBody().getData());
        assertEquals("Events retrieved successfully", response.getBody().getMessage());

        // Verify that the service method was called once
        verify(eventService, times(1)).getAllEvents();
    }

    @Test
    void testGetEventById() {
        // Data prep
        Long eventId = 1L;
        Event event = new Event(eventId, "Event 1", "Walk-in competition", TALENT_DAY,
                LocalDate.parse("2023-08-24"), LocalDate.parse("2023-08-26"), LocalDate.parse("2023-08-23"));


        when(eventService.getEventById(eventId)).thenReturn(event);

        // calling controller method
        ResponseEntity<ApiResponse<Event>> response = eventController.getEventById(eventId);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(event, response.getBody().getData());
        assertEquals("Event retrieved successfully", response.getBody().getMessage());

        // Verify that the service method was called once
        verify(eventService, times(1)).getEventById(eventId);
    }

    @Test
    void testCreateEvent() {
        // Data prep
        Event event = new Event(null, "Event1", "Showcase Talent", TALENT_DAY,
                LocalDate.parse("2023-08-24"), LocalDate.parse("2023-08-25"), LocalDate.parse("2023-07-23"));

        Event createdEvent = new Event(3L, "Event1", "Showcase Talent", TALENT_DAY,
                LocalDate.parse("2023-08-24"), LocalDate.parse("2023-08-25"), LocalDate.parse("2023-07-23"));


        when(eventService.createEvent(event)).thenReturn(createdEvent);

        // calling controller method
        ResponseEntity<ApiResponse<Event>> response = eventController.createEvent(event);


        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdEvent, response.getBody().getData());
        assertEquals("Event created successfully", response.getBody().getMessage());

        // Verify that the service method was called once
        verify(eventService, times(1)).createEvent(event);
    }

    @Test
    void testUpdateEvent() {
        // Data prep
        Long eventId = 1L;
        Event eventToUpdate = new Event(eventId, "Updated Event", "Updated Description", SCHOOL_DAY,
                LocalDate.parse("2023-08-20"), LocalDate.parse("2023-08-25"), LocalDate.parse("2023-08-18"));


        when(eventService.updateEvent(eventToUpdate)).thenReturn(eventToUpdate);

        // calling controller method
        ResponseEntity<ApiResponse<Event>> response = eventController.updateEvent(eventId, eventToUpdate);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(eventToUpdate, response.getBody().getData());
        assertEquals("Event updated successfully", response.getBody().getMessage());

        // Verify that the service method was called once
        verify(eventService, times(1)).updateEvent(eventToUpdate);
    }

    @Test
    void testDeleteEvent() {
        // Data prep
        Long eventId = 1L;


        doNothing().when(eventService).deleteEvent(eventId);

        // calling controller method
        ResponseEntity<ApiResponse<Void>> response = eventController.deleteEvent(eventId);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Event deleted successfully", response.getBody().getMessage());

        // Verify that the service method was called once
        verify(eventService, times(1)).deleteEvent(eventId);
    }
}
