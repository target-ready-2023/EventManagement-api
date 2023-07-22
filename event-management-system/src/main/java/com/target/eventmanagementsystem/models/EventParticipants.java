package com.target.eventmanagementsystem.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Events getEvent() {
        return event;
    }

    public void setEvent(Events event) {
        this.event = event;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

}
