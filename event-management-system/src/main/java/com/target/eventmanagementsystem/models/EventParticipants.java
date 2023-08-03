package com.target.eventmanagementsystem.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class EventParticipants {
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

    public EventParticipantKey getId() {
        return id;
    }

    public void setId(EventParticipantKey id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
            this.event = event;
        }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    private Integer result;
    // Getters and setters


}
