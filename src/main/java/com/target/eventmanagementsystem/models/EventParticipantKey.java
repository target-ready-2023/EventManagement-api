package com.target.eventmanagementsystem.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Embeddable
@Data
public class EventParticipantKey implements Serializable {
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "event_id")
    private Integer eventId;
    public EventParticipantKey() {

    }

    public EventParticipantKey(Integer userId, Integer eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }
}
