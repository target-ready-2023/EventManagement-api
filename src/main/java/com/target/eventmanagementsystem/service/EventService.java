package com.target.eventmanagementsystem.service;

import com.target.eventmanagementsystem.exception.EventNotFoundException;
import com.target.eventmanagementsystem.models.Event;
import com.target.eventmanagementsystem.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EventService {

    private EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents(){
        return eventRepository.findAll();
    }

    public Event getEvent(int id){

        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            return optionalEvent.get();
        } else {
            throw new EventNotFoundException("User not found with ID: " + id);
        }
    }

    public Event saveEvent(Event event){
        try{
            Event existingEvent = getEvent(event.getId());
            existingEvent.setTitle(event.getTitle());
            existingEvent.setEventType(event.getEventType());
            existingEvent.setDescription(event.getDescription());
            existingEvent.setStartDate(event.getStartDate());
            existingEvent.setEndDate(event.getEndDate());
            existingEvent.setLastDateForRegistration(event.getLastDateForRegistration());
            return eventRepository.save(event);
        }
        catch(NoSuchElementException e){
            throw new EventNotFoundException("Event not present");
        }
    }

    public String deleteEvent(int id){
        try{
            Event event = getEvent(id);
            eventRepository.deleteById(id);
            return "Event deleted with the ID " + id;
        }catch(NoSuchElementException e){
            return "Event with the ID " + id + " does not exist.";
        }
    }
}

