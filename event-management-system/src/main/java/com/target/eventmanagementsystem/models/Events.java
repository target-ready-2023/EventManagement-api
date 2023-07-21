package com.target.eventmanagementsystem.models;

import jakarta.persistence.Entity;
import org.springframework.data.annotation.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDate;

@Entity
public class Events {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String eventType;
    private LocalDate registerLastDate;
}
