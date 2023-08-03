//package com.target.eventmanagementsystem.models;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Embeddable;
//
//import java.io.Serializable;
//@Embeddable
//public class EventParticipantKey implements Serializable {
//    @Column(name = "user_id")
//    private Integer userId;
//
//    @Column(name = "event_id")
//    private Integer eventId;
//    public EventParticipantKey() {
//
//    }
//
//
//    public EventParticipantKey(Integer userId, Integer eventId) {
//        this.userId = userId;
//        this.eventId = eventId;
//    }
//
//    // Constructors, getters, and setters (you can use IDE-generated methods)
//
//    public Integer getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Integer userId) {
//        this.userId = userId;
//    }
//
//    public Integer getEventId() {
//        return eventId;
//    }
//
//    public void setEventId(Integer eventId) {
//        this.eventId = eventId;
//    }
//}

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
