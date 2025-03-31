package com.system.event_management.exception;


import lombok.Getter;

@Getter
public class InvalidEventIDException extends Exception {
    public InvalidEventIDException(String message) {
        super(message);
    }
}
