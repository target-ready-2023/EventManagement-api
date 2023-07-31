package com.target.eventmanagementsystem.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class EventParticipants {
    @EmbeddedId
    private EventParticipantKey id;

    @ManyToOne
    @MapsId("userId") // Maps the userId attribute in the composite key
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @MapsId("eventId") // Maps the eventId attribute in the composite key
    @JoinColumn(name = "event_id")
    private Event event;

    private Integer result;
}
