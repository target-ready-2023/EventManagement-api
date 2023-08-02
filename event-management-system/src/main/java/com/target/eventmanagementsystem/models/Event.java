package com.target.eventmanagementsystem.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String startDate;

    private String endDate;

    private String description;

    private String eventType;

    private String lastDateForRegistration;
}
