package com.target.eventmanagementsystem.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.target.eventmanagementsystem.models.Event;
import com.target.eventmanagementsystem.service.EventUserService;
import com.target.eventmanagementsystem.service.EventService;
import com.target.eventmanagementsystem.service.UserService;

@WebMvcTest
public class EventControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @MockBean 
    private EventUserService eventUserService;

    @MockBean
    private UserService userService;

    @Test
    void shouldGetAllEvents() throws Exception{
        
        List<Event> mockEvents = Arrays.asList(
            new Event(1, "Event 1", "Description 1", "2023-7-19", "2023-7-20", "Type 1", "2023-7-18"),
            new Event(2, "Event 2", "Description 2", "2023-7-21", "2023-7-22", "Type 2", "2023-7-20")
        );

        when(eventService.listAllEvents()).thenReturn(mockEvents);
        mockMvc.perform(MockMvcRequestBuilders.get("/events/getAllEvents"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));

        verify(eventService).listAllEvents();
    }

    @Test
    void shouldGetEventById() throws Exception{
        
        Event mockEvent =  new Event(1, "Event 1", "Description 1", "2023-7-19", "2023-7-20", "Type 1", "2023-7-18");

        when(eventService.listEventById(1)).thenReturn(mockEvent);
        mockMvc.perform(MockMvcRequestBuilders.get("/events/event/1"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk());
        verify(eventService).listEventById(1);
    }
}
