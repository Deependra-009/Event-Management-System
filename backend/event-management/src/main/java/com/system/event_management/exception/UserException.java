package com.system.event_management.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserException extends Exception{

    private final HttpStatus httpStatus;

    public UserException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus=httpStatus;
    }
}
