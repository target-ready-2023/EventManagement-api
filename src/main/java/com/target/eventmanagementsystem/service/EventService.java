package com.target.eventmanagementsystem.service;

import com.target.eventmanagementsystem.exceptions.BadRequestException;
import com.target.eventmanagementsystem.exceptions.NotFoundException;
import com.target.eventmanagementsystem.models.Event;
import com.target.eventmanagementsystem.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new NotFoundException("Event not found with ID: " + id));
    }

    public Event createEvent(Event event) {

        validateEvent(event);
        return eventRepository.save(event);
    }

    public Event updateEvent(Event event) {

        Long eventId = event.getId();

        if (eventId == null || eventRepository.findById(eventId) == null) {
            throw new NotFoundException("Event not found with ID: " + eventId);
        }

        Event existingEvent = eventRepository.findById(eventId).orElse(null);
        if (existingEvent == null) {
            throw new NotFoundException("Event not found with ID: " + eventId);
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
        if (eventRepository.findById(id) == null) {
            throw new NotFoundException("Event not found with ID: " + id);
        }

        eventRepository.deleteById(id);
    }

    public void validateEvent(Event event)
    {
        if (event.getTitle() == null || event.getStartDate() == null ||
                event.getEndDate() == null || event.getLastRegistrationDate() == null) {
            throw new BadRequestException("Invalid event data. Name and dates must be provided.");
        }
    }

}

