package com.target.eventmanagementsystem.service;

import com.target.eventmanagementsystem.models.Users;
import com.target.eventmanagementsystem.repository.EventParListRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class EventParticipantService {

    private final EventParListRepository eventParListRepository;

    @Autowired
    public EventParticipantService(EventParListRepository eventParListRepository){
        this.eventParListRepository = eventParListRepository;
    }

    //Retrieve the details of user registered for a particular event

    public List<Users> getUserRegisteredForEvent(Integer eventId){

        List<Users> registeredUsers;
        try{
            registeredUsers = eventParListRepository.findUsersByEvent(eventId);
        }catch (NoSuchElementException e){
            registeredUsers = Collections.emptyList();
        }

        return registeredUsers;

    }
}
