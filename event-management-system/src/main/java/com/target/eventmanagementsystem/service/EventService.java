package com.target.eventmanagementsystem.service;

import com.target.eventmanagementsystem.models.Event;
import com.target.eventmanagementsystem.payloads.ApiResponse;
import com.target.eventmanagementsystem.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public ApiResponse<Event> createEvent(Event event) {

        try {
            Event newEvent = eventRepository.save(event);
            return new ApiResponse<>(newEvent, "Event created successfully.");
        } catch (Exception e) {
            return new ApiResponse<>(null, "Failed to create a new event.");
        }
    }

    public ApiResponse<Event> getEventById(Long id) {
        try {
            Event event = eventRepository.findById(id).orElse(null);

            if (event != null) {
                return new ApiResponse<>(event, "Event found.");
            } else {
                return new ApiResponse<>(null, "Event not found.");
            }
        } catch (Exception e) {
            return new ApiResponse<>(null, "An error occurred while fetching Event data.");
        }
    }

    public ApiResponse<List<Event>> getAllEvents() {
        try {
            List<Event> events = eventRepository.findAll();

            if (!events.isEmpty()) {
                return new ApiResponse<>(events, "Events fetched successfully.");
            } else {
                return new ApiResponse<>(null, "No events found.");
            }
        } catch (Exception e) {
            return new ApiResponse<>(null, "An error occurred while fetching events data.");
        }
    }

    public ApiResponse<Event> updateEvent(Long id, Event event) {
        try {
            Optional<Event> optionalEvent = eventRepository.findById(id);

            if (optionalEvent.isPresent()) {

                Event existingEvent = optionalEvent.get();

                existingEvent.setTitle(event.getTitle());
                existingEvent.setEventType(event.getEventType());
                existingEvent.setDescription(event.getDescription());
                existingEvent.setStartDate(event.getStartDate());
                existingEvent.setEndDate(event.getEndDate());
                existingEvent.setLastRegistrationDate(event.getLastRegistrationDate());
                Event updatedEvent = eventRepository.save(existingEvent);

                return new ApiResponse<>(updatedEvent, "Event updated successfully.");
            } else {
                return new ApiResponse<>(null, "Event not found.");
            }
        } catch (Exception e) {
            return new ApiResponse<>(null, "An error occurred while updating the event data.");
        }
    }

    public ApiResponse<String> deleteEvent(Long id) {
        try {
            Event existingEvent = eventRepository.findById(id).orElse(null);

            if (existingEvent != null) {
                eventRepository.delete(existingEvent);
                return new ApiResponse<>("Event with ID " + id + " deleted successfully.");
            } else {
                return new ApiResponse<>(null, "Event not found.");
            }
        } catch (Exception e) {
            return new ApiResponse<>(null, "An error occurred while deleting the Event.");
        }
    }}
