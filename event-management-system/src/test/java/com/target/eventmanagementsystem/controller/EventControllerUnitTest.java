package com.target.eventmanagementsystem.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.target.eventmanagementsystem.models.Event;
import com.target.eventmanagementsystem.service.EventService;
public class EventControllerUnitTest {
    
    @Test
    void shouldGetAllEvents(){
        EventService eventService = Mockito.mock(EventService.class);
        List<Event> mockEvents = Arrays.asList(
            new Event(1, "Event 1", "2023-7-20", "2023-7-19", "Description 1", "Type 1", "2023-7-18"),
            new Event(2, "Event 2", "2023-7-22", "2023-7-21", "Description", "Type 2", "2023-7-20")
        );
        when(eventService.getAllEvents()).thenReturn(mockEvents);
        EventController eventController = new EventController(eventService);
        assertEquals(mockEvents, eventController.getAllEvents());
    }
    
    @Test
    void shouldGetEventById(){
        EventService eventService = Mockito.mock(EventService.class);
        Event mockEvent =  new Event(1, "Event 1", "2023-7-20", "2023-7-19", "Description 1", "Type 1", "2023-7-18");

        when(eventService.getEvent(1)).thenReturn(mockEvent);
        EventController eventController = new EventController(eventService);
        assertEquals(mockEvent, eventController.get(1).getBody());
    }
}
