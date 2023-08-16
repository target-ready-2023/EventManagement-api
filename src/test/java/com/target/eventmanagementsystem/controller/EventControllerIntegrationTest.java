package com.target.eventmanagementsystem.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.target.eventmanagementsystem.models.Event;
import com.target.eventmanagementsystem.service.EventRegistrationService;
import com.target.eventmanagementsystem.service.EventService;
import com.target.eventmanagementsystem.service.UserService;

@WebMvcTest
public class EventControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @MockBean
    private EventRegistrationService eventRegistrationService;

    @MockBean
    private UserService userService;

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldGetAllEvents() throws Exception{

        List<Event> mockEvents = Arrays.asList(
            new Event((long) 1, "Event 1", "Description 1", "School day", LocalDate.of(2023, 9, 20), LocalDate.of(2023, 9, 25), LocalDate.of(2023, 9, 10)),
            new Event((long) 2, "Event 2", "Description 2", "Sports day", LocalDate.of(2023, 8, 11), LocalDate.of(2023, 8, 12), LocalDate.of(2023, 8, 11))
        );

        when(eventService.getAllEvents()).thenReturn(mockEvents);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/events"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data", hasSize(2)));

        verify(eventService).getAllEvents();
    }

    @Test
    void shouldGetEventById() throws Exception{

        Event mockEvent =  new Event((long) 1, "Event 1", "Description 1", "Sports day", LocalDate.of(2023, 9, 20), LocalDate.of(2023, 9, 25), LocalDate.of(2023, 9, 15));

        when(eventService.getEventById((long) 1)).thenReturn(mockEvent);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/events/1"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(mockEvent.getId()));

        verify(eventService).getEventById((long) 1);
    }

    @Test
    void shouldCreateEvent() throws Exception {
        Event mockEvent =  new Event(null, "Event 1", "Description 1", "Sports day", LocalDate.of(2023, 9, 20), LocalDate.of(2023, 9, 25), LocalDate.of(2023, 9, 10));

        Event createdEvent =  new Event((long) 1, "Event 1", "Description 1", "School day", LocalDate.of(2023, 9, 20), LocalDate.of(2023, 9, 25), LocalDate.of(2023, 9, 10));

        when(eventService.createEvent(mockEvent)).thenReturn(createdEvent);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/events")
        .content(asJsonString(mockEvent))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(eventService).createEvent(mockEvent);
    }

    @Test
    void shouldUpdateEvent() throws Exception {
        long eventId = (long) 1;
        Event updateEvent =  new Event(null, "Event 1", "Description 1", "type 1", LocalDate.of(2023, 9, 20), LocalDate.of(2023, 9, 25), LocalDate.of(2023, 9, 10));
        Event updatedEvent =  new Event((long) 1, "Event 1", "Description 1", "type 1", LocalDate.of(2023, 9, 20), LocalDate.of(2023, 9, 25), LocalDate.of(2023, 9, 10));

        when(eventService.updateEvent(updateEvent)).thenReturn(updateEvent);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/events/{eventId}",eventId)
        .content(asJsonString(updateEvent))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());

        verify(eventService).updateEvent(updatedEvent);
    }

    @Test
    void shouldDeleteEvent() throws Exception {
        long eventId = (long) 1;

        doNothing().when(eventService).deleteEvent(eventId);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/events/{id}", eventId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(eventService).deleteEvent((long)1);
}
}
