package com.target.eventmanagementsystem.controller;

import com.target.eventmanagementsystem.models.Event;
import com.target.eventmanagementsystem.models.Registration;
import com.target.eventmanagementsystem.models.User;
import com.target.eventmanagementsystem.payloads.ApiResponse;
import com.target.eventmanagementsystem.service.EventRegistrationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventRegistrationControllerTest {

    @Mock
    private EventRegistrationService eventRegistrationService;

    @InjectMocks
    private EventRegistrationController eventRegistrationController;

    @Test
    void testRegisterUserForEvent() {
        // Prepare data
        Registration registration = new Registration();
        registration.setEventId(1L);
        registration.setUserId(2L);

        // Mock the service method
        doNothing().when(eventRegistrationService).registerUserForEvent(registration.getEventId(), registration.getUserId());

        // Call the controller method
        ResponseEntity<ApiResponse<String>> responseEntity = eventRegistrationController.registerUserForEvent(registration);

        // Verify
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("User registered for the event successfully.", responseEntity.getBody().getMessage());
        assertNull(responseEntity.getBody().getData());

        verify(eventRegistrationService, times(1)).registerUserForEvent(registration.getEventId(), registration.getUserId());
    }

    @Test
    void testDeregisterUserFromEvent() {
        // Prepare data
        Registration registration = new Registration();
        registration.setEventId(1L);
        registration.setUserId(2L);

        // Mock the service method
        doNothing().when(eventRegistrationService).deregisterUserFromEvent(registration.getEventId(), registration.getUserId());

        // Call the controller method
        ResponseEntity<ApiResponse<String>> responseEntity = eventRegistrationController.deregisterUserFromEvent(registration);

        // Verify
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("User deregistered for the event successfully.", responseEntity.getBody().getMessage());
        assertNull(responseEntity.getBody().getData());

        verify(eventRegistrationService, times(1)).deregisterUserFromEvent(registration.getEventId(), registration.getUserId());
    }

    @Test
    void testGetEventsForUser() {
        // Prepare data
        Long userId = 1L;
        List<Event> events = new ArrayList<>();
        events.add(new Event(1L, "Event 1", "Description 1", "Event Type 1", null, null, null));

        // Mock the service method
        when(eventRegistrationService.getAllEventsForUser(userId)).thenReturn(events);

        // Call the controller method
        ResponseEntity<ApiResponse<List<Event>>> responseEntity = eventRegistrationController.getEventsForUser(userId);

        // Verify
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Events registered by the user retrieved successfully.", responseEntity.getBody().getMessage());
        assertEquals(events, responseEntity.getBody().getData());

        verify(eventRegistrationService, times(1)).getAllEventsForUser(userId);
    }

    @Test
    void testGetUsersForEvent() {
        // Prepare data
        Long eventId = 1L;
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "John", "Doe", null, null, "john@example.com", "password", null));

        // Mock the service method
        when(eventRegistrationService.getAllUsersForEvent(eventId)).thenReturn(users);

        // Call the controller method
        ResponseEntity<ApiResponse<List<User>>> responseEntity = eventRegistrationController.getUsersForEvent(eventId);

        // Verify
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Users registered for the event retrieved successfully.", responseEntity.getBody().getMessage());
        assertEquals(users, responseEntity.getBody().getData());

        verify(eventRegistrationService, times(1)).getAllUsersForEvent(eventId);
    }
}
