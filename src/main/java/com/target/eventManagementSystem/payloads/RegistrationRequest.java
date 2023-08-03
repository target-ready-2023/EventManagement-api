package com.target.eventManagementSystem.payloads;

import lombok.Data;

@Data
public class RegistrationRequest {
    private Long eventId;
    private Long userId;

    // getters, setters, etc.
}