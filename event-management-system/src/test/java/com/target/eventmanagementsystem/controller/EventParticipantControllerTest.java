package com.target.eventmanagementsystem.controller;

import com.target.eventmanagementsystem.models.Users;
import com.target.eventmanagementsystem.service.EventParticipantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventParticipantControllerTest {

    @Mock
    private EventParticipantService mockEventParticipantService;

    private EventParticipantController eventParticipantController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        eventParticipantController = new EventParticipantController(mockEventParticipantService);
    }

    @Test
    public void testGetUsersRegisteredForEventWithParticipants(){
        //Arrange
        Integer eventId = 1;
        List<Users> expectedParticipants = new ArrayList<>();
        expectedParticipants.add(new Users());

        when(mockEventParticipantService.getUserRegisteredForEvent(eventId))
                .thenReturn(expectedParticipants);

        //Act
        ResponseEntity<?> responseEntity = eventParticipantController.getUsersRegisteredForEvent(eventId);

        //Assert
        assertEquals(expectedParticipants,responseEntity.getBody());
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }


    @Test
    public void testGetUsersRegisteredForEventWithNoParticipants(){
        Integer eventId = 456;
        when(mockEventParticipantService.getUserRegisteredForEvent(eventId))
                .thenReturn(new ArrayList<>());

        ResponseEntity<?> responseEntity = eventParticipantController.getUsersRegisteredForEvent(eventId);

        //Act
        ResponseEntity<?> responseEntityNoParticipants = eventParticipantController.getUsersRegisteredForEvent(eventId);

        assertEquals("No participants in the current event.",responseEntityNoParticipants.getBody());
        assertEquals(HttpStatus.NOT_FOUND,responseEntityNoParticipants.getStatusCode());
    }

}