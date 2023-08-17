package com.target.eventmanagementsystem.repository;

import com.target.eventmanagementsystem.models.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@DataJpaTest
class EventRegistrationRepositoryTest {

    @MockBean
    private EventRegistrationRepository eventRegistrationRepository;

    @Test
    void testExistsByEventIdAndUserId() {
        Long eventId = 1L;
        Long userId = 2L;
        when(eventRegistrationRepository.existsByEventIdAndUserId(eventId, userId)).thenReturn(true);

        boolean result = eventRegistrationRepository.existsByEventIdAndUserId(eventId, userId);

        assertTrue(result);
    }

    @Test
    void testFindByEventIdAndUserId() {
        Long eventId = 1L;
        Long userId = 2L;
        Registration registration = new Registration();
        registration.setEventId(eventId);
        registration.setUserId(userId);
        when(eventRegistrationRepository.findByEventIdAndUserId(eventId, userId)).thenReturn(registration);

        Registration result = eventRegistrationRepository.findByEventIdAndUserId(eventId, userId);

        assertEquals(registration, result);
    }

    @Test
    void testFindAllEventsForUser() {
        Long userId = 2L;
        List<Event> events = new ArrayList<>();
        Event event = new Event(1L, "Event Title", "Event Description", "Workshop",
                LocalDate.now(), LocalDate.now().plusDays(1), LocalDate.now().plusDays(1));
        events.add(event);
        when(eventRegistrationRepository.findAllEventsForUser(userId)).thenReturn(events);

        List<Event> result = eventRegistrationRepository.findAllEventsForUser(userId);

        assertEquals(events, result);
    }

    @Test
    void testFindAllUsersForEvent() {
        Long eventId = 1L;
        List<User> users = new ArrayList<>();
        User user = new User(2L, "John", "Doe", LocalDate.of(1990, 5, 15),
                Gender.MALE, "john@example.com", "password", UserRoles.STUDENT);
        users.add(user);
        when(eventRegistrationRepository.findAllUsersForEvent(eventId)).thenReturn(users);

        List<User> result = eventRegistrationRepository.findAllUsersForEvent(eventId);

        assertEquals(users, result);
    }
}
