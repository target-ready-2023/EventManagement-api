package com.target.eventmanagementsystem.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Data;
@Entity
@Data
public class EventParticipant {
    @EmbeddedId
    private EventParticipantKey id;

    @ManyToOne
    @MapsId("userId") // Maps the userId attribute in the composite key
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("eventId") // Maps the eventId attribute in the composite key
    @JoinColumn(name = "event_id")
    private Event event;

    private int result;
}