package com.target.eventmanagementsystem.service;

import com.target.eventmanagementsystem.exceptions.ApiException;
import com.target.eventmanagementsystem.models.Event;
import com.target.eventmanagementsystem.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Event not found with ID: " + id));
    }

    public Event createEvent(Event event) {

        validateEvent(event);
        return eventRepository.save(event);
    }


    public Event updateEvent(Event event) {

        Long eventId = event.getId();

        if (eventId == null ) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Event id must be provided");
        }

        Event existingEvent = eventRepository.findById(eventId).orElse(null);
        if (existingEvent == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Event not found with ID: " + eventId);
        }

        validateEvent(event);

        existingEvent.setEventType(event.getEventType());
        existingEvent.setDescription(event.getDescription());
        existingEvent.setTitle(event.getTitle());
        existingEvent.setStartDate(event.getStartDate());
        existingEvent.setEndDate(event.getEndDate());
        existingEvent.setLastRegistrationDate(event.getLastRegistrationDate());

        return eventRepository.save(existingEvent);

    }

    public void deleteEvent(Long id) {

        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Event not found with ID: " + id));

        eventRepository.deleteById(id);
    }

    // Upcoming events list comparing with start date and today's date
    public List<Event> getUpcomingEvents() {
        LocalDate today = LocalDate.now();
        return eventRepository.findByStartDateAfterOrderByStartDate(today);
    }

    // Past events list comparing with end date and today's date
    public List<Event> getPastEvents() {
        LocalDate today = LocalDate.now();
        return eventRepository.findByEndDateBeforeOrderByEndDateDesc(today);
    }


    public void validateEvent(Event event)
    {
        if (event.getTitle() == null || event.getStartDate() == null ||
                event.getEndDate() == null || event.getLastRegistrationDate() == null || event.getEventType() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid event data. Name and date must be provided.");
        }

        // Unique event
        if (isDuplicateEvent(event)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Duplicate event found.");
        }

        // Start and end date should be after today's date
        LocalDate today = LocalDate.now();
        if (event.getStartDate().isBefore(today) || event.getEndDate().isBefore(today) || event.getLastRegistrationDate().isBefore(today)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Event dates must be in the future.");
        }

        // End date should be after start date
        if (event.getEndDate().isBefore(event.getStartDate())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "End date should be after start date");
        }

        // Last registration date should be before end date
        if (event.getLastRegistrationDate().isAfter(event.getEndDate())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Last registration date should be before end date");
        }

        // Last registration date should be before start date
        if (event.getLastRegistrationDate().isAfter(event.getStartDate())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Last registration date cannot be after event start date.");
        }

        // Event type should contain "Sports day", "School day", "Talent day"
        List<String> validEventTypes = Arrays.asList("Sports day", "School day", "Talent day");
        if (!validEventTypes.contains(event.getEventType())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid event type.");
        }

    }

    private boolean isDuplicateEvent(Event newEvent) {

        List<Event> existingEvents = eventRepository.findByTitleAndStartDate(newEvent.getTitle(), newEvent.getStartDate());

        return existingEvents.stream()
                .anyMatch(existingEvent ->
                        !existingEvent.getId().equals(newEvent.getId()) &&
                                existingEvent.getEndDate().isEqual(newEvent.getEndDate())
                );
    }
}

