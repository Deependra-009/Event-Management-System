package com.system.event_management.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class JwtAuthenticationException extends Exception {
    private final HttpStatus httpStatus;
    public JwtAuthenticationException(String message,HttpStatus httpStatus) {
        super(message);
        this.httpStatus=httpStatus;
    }
}
