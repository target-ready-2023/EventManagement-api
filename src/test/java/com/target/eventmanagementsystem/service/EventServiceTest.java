package com.target.eventmanagementsystem.service;

import com.target.eventmanagementsystem.models.Event;
import com.target.eventmanagementsystem.models.EventTypes;
import com.target.eventmanagementsystem.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        eventService = new EventService(eventRepository);
    }

    @Test
    void testGetAllEvents() {
        List<Event> events = new ArrayList<>();
        events.add(new Event());
        when(eventRepository.findAll()).thenReturn(events);

        List<Event> result = eventService.getAllEvents();

        assertEquals(events, result);
    }

    @Test
    void testGetEventById() {
        Long eventId = 1L;
        Event event = new Event(eventId, "Event Title", "Event Description", "Workshop",
                LocalDate.now(), LocalDate.now().plusDays(1), LocalDate.now().plusDays(1));
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        Event result = eventService.getEventById(eventId);

        assertEquals(event, result);
    }

    @Test
    void testCreateEvent() {
        Event event = new Event(null, "Event Title", "Event Description", EventTypes.SPORTS_DAY,
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(2), LocalDate.now());
        when(eventRepository.save(event)).thenReturn(event);

        Event result = eventService.createEvent(event);

        assertEquals(event, result);
    }

    @Test
    void testUpdateEvent() {
        Long eventId = 1L;
        Event existingEvent = new Event(eventId, "Old Title", "Old Description", EventTypes.SPORTS_DAY,
                LocalDate.now(), LocalDate.now().plusDays(1), LocalDate.now().plusDays(1));
        Event updatedEvent = new Event(eventId, "Updated Title", "Updated Description", EventTypes.SCHOOL_DAY,
                LocalDate.now().plusDays(2), LocalDate.now().plusDays(3), LocalDate.now());

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(existingEvent));
        when(eventRepository.save(existingEvent)).thenReturn(updatedEvent);

        Event result = eventService.updateEvent(updatedEvent);

        assertEquals(updatedEvent, result);
        assertEquals(updatedEvent.getTitle(), result.getTitle());
    }

    @Test
    void testDeleteEvent() {
        Long eventId = 1L;
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(new Event()));

        assertDoesNotThrow(() -> eventService.deleteEvent(eventId));
    }

    @Test
    void testGetUpcomingEvents() {
        LocalDate today = LocalDate.now();
        List<Event> upcomingEvents = new ArrayList<>();
        Event event = new Event(1L, "Upcoming Event", "Event Description", EventTypes.SPORTS_DAY,
                today.plusDays(1), today.plusDays(2), today.plusDays(2));
        upcomingEvents.add(event);
        when(eventRepository.findByStartDateAfterOrderByStartDate(today)).thenReturn(upcomingEvents);

        List<Event> result = eventService.getUpcomingEvents();

        assertEquals(upcomingEvents, result);
    }

    @Test
    void testGetPastEvents() {
        LocalDate today = LocalDate.now();
        List<Event> pastEvents = new ArrayList<>();
        Event event = new Event(1L, "Past Event", "Event Description", EventTypes.SPORTS_DAY,
                today.minusDays(2), today.minusDays(1), today.minusDays(1));
        pastEvents.add(event);
        when(eventRepository.findByEndDateBeforeOrderByEndDateDesc(today)).thenReturn(pastEvents);

        List<Event> result = eventService.getPastEvents();

        assertEquals(pastEvents, result);
    }

    @Test
    void testGetOngoingEvents() {
        LocalDate today = LocalDate.now();
        List<Event> ongoingEvents = new ArrayList<>();
        Event event = new Event(1L, "Ongoing Event", "Event Description", EventTypes.SPORTS_DAY,
                today.minusDays(1), today.plusDays(1), today.plusDays(1));
        ongoingEvents.add(event);
        when(eventRepository.findByStartDateBeforeAndEndDateAfterOrderByStartDate(today, today)).thenReturn(ongoingEvents);

        List<Event> result = eventService.getOngoingEvents();

        assertEquals(ongoingEvents, result);
    }


}
