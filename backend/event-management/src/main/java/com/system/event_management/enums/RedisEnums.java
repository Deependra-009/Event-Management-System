package com.system.event_management.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
public enum RedisEnums {

    GET_ALL_EVENTS("GET_ALL_EVENTS"),
    GET_ALL_USERS("GET_ALL_USERS");

    private final String key;

    RedisEnums(String key) {
        this.key = key;
    }

}
