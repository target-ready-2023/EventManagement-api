package com.target.eventmanagementsystem.repository;

import com.target.eventmanagementsystem.models.Event;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DataJpaTest
class EventRepositoryTest {

    @MockBean
    private EventRepository eventRepository;

    @Test
    void testFindByTitleAndStartDate() {
        String title = "Sample Event";
        LocalDate startDate = LocalDate.now();
        List<Event> events = new ArrayList<>();
        Event event = new Event(1L, title, "Event Description", "Workshop",
                startDate, startDate.plusDays(1), startDate.plusDays(1));
        events.add(event);
        when(eventRepository.findByTitleAndStartDate(title, startDate)).thenReturn(events);

        List<Event> result = eventRepository.findByTitleAndStartDate(title, startDate);

        assertEquals(events, result);
    }

    @Test
    void testFindByStartDateAfterOrderByStartDate() {
        LocalDate startDate = LocalDate.now();
        List<Event> events = new ArrayList<>();
        Event event = new Event(1L, "Event Title", "Event Description", "Workshop",
                startDate.plusDays(1), startDate.plusDays(2), startDate.plusDays(2));
        events.add(event);
        when(eventRepository.findByStartDateAfterOrderByStartDate(startDate)).thenReturn(events);

        List<Event> result = eventRepository.findByStartDateAfterOrderByStartDate(startDate);

        assertEquals(events, result);
    }

    @Test
    void testFindByEndDateBeforeOrderByEndDateDesc() {
        LocalDate endDate = LocalDate.now();
        List<Event> events = new ArrayList<>();
        Event event = new Event(1L, "Event Title", "Event Description", "Workshop",
                endDate.minusDays(2), endDate.minusDays(1), endDate);
        events.add(event);
        when(eventRepository.findByEndDateBeforeOrderByEndDateDesc(endDate)).thenReturn(events);

        List<Event> result = eventRepository.findByEndDateBeforeOrderByEndDateDesc(endDate);

        assertEquals(events, result);
    }

    @Test
    void testFindByStartDateBeforeAndEndDateAfterOrderByStartDate() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(2);
        List<Event> events = new ArrayList<>();
        Event event = new Event(1L, "Event Title", "Event Description", "Workshop",
                startDate.minusDays(1), endDate.plusDays(1), endDate.plusDays(1));
        events.add(event);
        when(eventRepository.findByStartDateBeforeAndEndDateAfterOrderByStartDate(startDate, endDate)).thenReturn(events);

        List<Event> result = eventRepository.findByStartDateBeforeAndEndDateAfterOrderByStartDate(startDate, endDate);

        assertEquals(events, result);
    }
}
