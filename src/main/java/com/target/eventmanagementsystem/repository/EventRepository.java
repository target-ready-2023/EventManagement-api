package com.target.eventmanagementsystem.repository;

import com.target.eventmanagementsystem.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event,Long> {
    List<Event> findByTitleAndStartDate(String title, LocalDate startDate);
    List<Event> findByStartDateAfterOrderByStartDate(LocalDate startDate);
    List<Event> findByEndDateBeforeOrderByEndDateDesc(LocalDate endDate);
    List<Event> findByStartDateBeforeAndEndDateAfterOrderByStartDate(LocalDate startDate, LocalDate endDate);

}
