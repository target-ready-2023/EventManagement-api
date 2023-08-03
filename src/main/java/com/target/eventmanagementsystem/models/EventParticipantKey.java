package com.target.eventmanagementsystem.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventParticipantKey implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "event_id")
    private Long eventId;

}
