package com.target.eventmanagementsystem.service;

import com.target.eventmanagementsystem.exceptions.ApiException;
import com.target.eventmanagementsystem.models.Gender;
import com.target.eventmanagementsystem.models.Event;
import com.target.eventmanagementsystem.models.User;
import com.target.eventmanagementsystem.models.Registration;
import com.target.eventmanagementsystem.models.UserRoles;
import com.target.eventmanagementsystem.repository.EventRegistrationRepository;
import com.target.eventmanagementsystem.repository.EventRepository;
import com.target.eventmanagementsystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventRegistrationServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventRegistrationRepository registrationRepository;

    @InjectMocks
    private EventRegistrationService eventRegistrationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        eventRegistrationService = new EventRegistrationService(eventRepository, userRepository, registrationRepository);
    }

    @Test
    void testRegisterUserForEvent_ValidRegistration() {
        Long eventId = 1L;
        Long userId = 2L;
        Event event = new Event(eventId, "Event Title", "Event Description", "Sports", LocalDate.now(),
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(1));
        User user = new User(userId, "John", "Doe", LocalDate.of(1990, 5, 15),
                Gender.MALE, "john@example.com", "password", UserRoles.STUDENT);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(registrationRepository.existsByEventIdAndUserId(eventId, userId)).thenReturn(false);

        assertDoesNotThrow(() -> eventRegistrationService.registerUserForEvent(eventId, userId));
    }

    @Test
    void testRegisterUserForEvent_UserAlreadyRegistered() {
        Long eventId = 1L;
        Long userId = 2L;

        when(registrationRepository.existsByEventIdAndUserId(eventId, userId)).thenReturn(true);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(new Event()));
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        when(eventRegistrationService.isUserAlreadyRegistered(eventId, userId)).thenReturn(true);

        assertThrows(ApiException.class, () -> eventRegistrationService.registerUserForEvent(eventId, userId));
        verify(registrationRepository, never()).save(any(Registration.class));
    }

    @Test
    void testRegisterUserForEvent_NonStudentUser() {
        Long eventId = 3L;
        Long userId = 2L;
        Event event = new Event(eventId, "Event Title", "Event Description", "Sports", LocalDate.now(),
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(1));
        User user = new User(userId, "John", "Doe", LocalDate.of(1990, 5, 15),
                Gender.MALE, "john@example.com", "password", UserRoles.ADMIN);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertThrows(ApiException.class, () -> eventRegistrationService.registerUserForEvent(eventId, userId));
    }

    @Test
    void testDeregisterUserFromEvent_ValidDeregistration() {
        Long eventId = 1L;
        Long userId = 2L;
        Registration registration = new Registration();
        registration.setEventId(eventId);
        registration.setUserId(userId);

        when(registrationRepository.findByEventIdAndUserId(eventId, userId)).thenReturn(registration);

        assertDoesNotThrow(() -> eventRegistrationService.deregisterUserFromEvent(eventId, userId));
    }

    @Test
    void testDeregisterUserFromEvent_UserNotRegistered() {
        Long eventId = 1L;
        Long userId = 2L;

        when(registrationRepository.findByEventIdAndUserId(eventId, userId)).thenReturn(null);

        assertThrows(ApiException.class, () -> eventRegistrationService.deregisterUserFromEvent(eventId, userId));
    }

    @Test
    void testGetAllEventsForUser() {
        Long userId = 2L;
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(new Event(1L, "Event 1", "Description", "Sports", LocalDate.now(),
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(1)));
        expectedEvents.add(new Event(2L, "Event 2", "Description", "School", LocalDate.now(),
                LocalDate.now().plusDays(2), LocalDate.now().plusDays(2)));

        when(registrationRepository.findAllEventsForUser(userId)).thenReturn(expectedEvents);

        List<Event> actualEvents = eventRegistrationService.getAllEventsForUser(userId);

        assertEquals(expectedEvents, actualEvents);
    }

    @Test
    void testGetAllUsersForEvent() {
        Long eventId = 1L;
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(new User(1L, "John", "Doe", LocalDate.of(1990, 5, 15),
                Gender.MALE, "john@example.com", "password", UserRoles.STUDENT));
        expectedUsers.add(new User(2L, "Jane", "Smith", LocalDate.of(1995, 8, 25),
                Gender.FEMALE, "jane@example.com", "password", UserRoles.STUDENT));

        when(registrationRepository.findAllUsersForEvent(eventId)).thenReturn(expectedUsers);

        List<User> actualUsers = eventRegistrationService.getAllUsersForEvent(eventId);

        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void testRegisterUserForEvent_RegistrationClosed() {
        Long eventId = 1L;
        Long userId = 1L;
        Event event = new Event();
        User user;

        event.setLastRegistrationDate(LocalDate.now().minusDays(1));


        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user=new User()));
        user.setRole(UserRoles.STUDENT);

        when(eventRegistrationService.isUserAlreadyRegistered(eventId, userId)).thenReturn(false);

        ApiException exception=assertThrows(ApiException.class, () -> eventRegistrationService.registerUserForEvent(eventId, userId));
        verify(registrationRepository, never()).save(any(Registration.class));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Registration for the event is already closed.", exception.getMessage());
    }

}
