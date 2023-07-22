package com.target.eventmanagementsystem.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class EventParticipants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Events event;

    private Integer result; // Initially null, updated after the end of the event
}
