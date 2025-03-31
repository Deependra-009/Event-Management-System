package com.system.event_management.exception;

import lombok.Getter;

@Getter
public class EventNotFoundException extends Exception {
    public EventNotFoundException(String message) {
        super(message);
    }
}
