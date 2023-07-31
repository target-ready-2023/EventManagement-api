package com.target.eventmanagementsystem.service;

import com.target.eventmanagementsystem.models.Event;
import com.target.eventmanagementsystem.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public List<Event> listAllEvents(){
        return eventRepository.findAll();
    }

    public Event listEventById(Integer id){
        return eventRepository.findById(id).get();
    }

    public Event saveEvent(Event event){
        return eventRepository.save(event);
    }

    public void deleteEvent(Integer id){
        eventRepository.deleteById(id);
    }
}
