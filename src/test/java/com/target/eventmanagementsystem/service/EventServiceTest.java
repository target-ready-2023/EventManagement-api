package com.target.eventmanagementsystem.service;

import com.target.eventmanagementsystem.exceptions.ApiException;
import com.target.eventmanagementsystem.models.Event;
import com.target.eventmanagementsystem.models.EventTypes;
import com.target.eventmanagementsystem.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.*;

import static com.target.eventmanagementsystem.models.EventTypes.TALENT_DAY;
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
    void testUpdateEvent_NullEventId() {
        Event updatedEvent = new Event();
        updatedEvent.setTitle("Updated Title");
        updatedEvent.setStartDate(LocalDate.now());

        ApiException exception = assertThrows(ApiException.class, () -> eventService.updateEvent(updatedEvent));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Event id must be provided", exception.getMessage());
    }

    @Test
    void testUpdateEvent_EventNotFound() {
        Long eventId = 1L;
        Event updatedEvent = new Event();
        updatedEvent.setId(eventId);
        updatedEvent.setTitle("Updated Title");
        updatedEvent.setStartDate(LocalDate.now());

        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> eventService.updateEvent(updatedEvent));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Event not found with ID: " + eventId, exception.getMessage());
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
    public void testValidateEvent_MissingData() {
        Event event = new Event();
        ApiException exception = assertThrows(ApiException.class, () -> eventService.validateEvent(event));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertTrue(exception.getMessage().contains("Invalid event data. Name and date must be provided."));
    }

    @Test
    public void testValidateEvent_EndDateBeforeStartDate() {
        Event event = new Event();
        event.setTitle("Event Title");
        event.setEventType(EventTypes.TALENT_DAY);
        event.setStartDate(LocalDate.now().plusDays(2));
        event.setEndDate(LocalDate.now().plusDays(1));
        event.setLastRegistrationDate(LocalDate.now().plusDays(3));

        ApiException exception = assertThrows(ApiException.class, () -> eventService.validateEvent(event));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertTrue(exception.getMessage().contains("End date should be after start date"));
    }

    @Test
    public void testValidateEvent_LastRegDateAfterStartDate() {
        Event event = new Event();
        event.setTitle("Event Title");
        event.setEventType(EventTypes.TALENT_DAY);
        event.setStartDate(LocalDate.now());
        event.setEndDate(LocalDate.now().plusDays(2));
        event.setLastRegistrationDate(LocalDate.now().plusDays(3));

        ApiException exception = assertThrows(ApiException.class, () -> eventService.validateEvent(event));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertTrue(exception.getMessage().contains("Last registration date cannot be after event start date."));
    }

    @Test
    public void testValidateEvent_InvalidEventType() {
        Event event = new Event();
        event.setTitle("Event Title");
        event.setEventType("Invalid Type");
        event.setStartDate(LocalDate.now().plusDays(1));
        event.setEndDate(LocalDate.now().plusDays(2));
        event.setLastRegistrationDate(LocalDate.now());

        ApiException exception = assertThrows(ApiException.class, () -> eventService.validateEvent(event));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertTrue(exception.getMessage().contains("Invalid event type."));
    }

    @Test
    public void testValidateEvent_DuplicateEvent() {
        Event event = new Event();
        event.setTitle("Event Title");
        event.setEventType(EventTypes.TALENT_DAY);
        event.setStartDate(LocalDate.now().plusDays(1));
        event.setEndDate(LocalDate.now().plusDays(2));
        event.setLastRegistrationDate(LocalDate.now());

        when(eventRepository.findByTitleAndStartDate(event.getTitle(), event.getStartDate())).thenReturn(Collections.singletonList(event));

        ApiException exception = assertThrows(ApiException.class, () -> eventService.validateEvent(event));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertTrue(exception.getMessage().contains("Duplicate event found."));
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

    @Test
    public void testValidateEvent_ValidEvent() {
        Event event = new Event();
        event.setTitle("Event Title");
        event.setEventType(TALENT_DAY);
        event.setStartDate(LocalDate.now().plusDays(1));
        event.setEndDate(LocalDate.now().plusDays(2));
        event.setLastRegistrationDate(LocalDate.now());

        assertDoesNotThrow(() -> eventService.validateEvent(event));
    }

    @Test
    public void testValidateEvent_InvalidDates() {
        Event event = new Event();
        event.setTitle("Event Title");
        event.setEventType(TALENT_DAY);
        event.setStartDate(LocalDate.now().minusDays(1));
        event.setEndDate(LocalDate.now().minusDays(2));
        event.setLastRegistrationDate(LocalDate.now().minusDays(3));

        ApiException exception = assertThrows(ApiException.class, () -> eventService.validateEvent(event));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertTrue(exception.getMessage().contains("Event dates must be in the future."));
    }

    @Test
    public void testIsDuplicateEvent_NoDuplicates() {
        Event newEvent = new Event();
        newEvent.setTitle("New Event");
        newEvent.setStartDate(LocalDate.now());
        newEvent.setEndDate(LocalDate.now().plusDays(3));

        Event event1 = new Event();
        event1.setId(1L);
        event1.setTitle("Event 1");
        event1.setStartDate(LocalDate.now());
        event1.setEndDate(LocalDate.now().plusDays(1));

        Event event2 = new Event();
        event2.setId(2L);
        event2.setTitle("Event 2");
        event2.setStartDate(LocalDate.now());
        event2.setEndDate(LocalDate.now().plusDays(2));

        List<Event> existingEvents = Arrays.asList(event1, event2);

        when(eventRepository.findByTitleAndStartDate(newEvent.getTitle(), newEvent.getStartDate())).thenReturn(existingEvents);

        boolean result = eventService.isDuplicateEvent(newEvent);
        assertFalse(result);
    }

    @Test
    public void testIsDuplicateEvent_DuplicateFound() {
        Event newEvent = new Event();
        newEvent.setTitle("Event 1");
        newEvent.setStartDate(LocalDate.now());
        newEvent.setEndDate(LocalDate.now().plusDays(1));
        Event event;

        List<Event> existingEvents = Collections.singletonList(
                event=new Event());
        event.setId(1L);
        event.setTitle("Event 1");
        event.setStartDate(LocalDate.now());
        event.setEndDate(LocalDate.now().plusDays(1));

        when(eventRepository.findByTitleAndStartDate(newEvent.getTitle(), newEvent.getStartDate())).thenReturn(existingEvents);

        boolean result = eventService.isDuplicateEvent(newEvent);
        assertTrue(result);
    }


}
