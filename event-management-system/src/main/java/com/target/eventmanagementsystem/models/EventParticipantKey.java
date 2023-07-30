package com.target.eventmanagementsystem.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
@Embeddable
public class EventParticipantKey implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "event_id")
    private Long eventId;
    public EventParticipantKey() {

    }


    public EventParticipantKey(Long userId, Long eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }

    // Constructors, getters, and setters (you can use IDE-generated methods)

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
