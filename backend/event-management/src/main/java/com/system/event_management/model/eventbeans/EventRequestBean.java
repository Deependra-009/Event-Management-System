package com.system.event_management.model.eventbeans;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventRequestBean {

    private Long eventID;
    private String eventName;
    private String eventLocation;
    private LocalDateTime eventDateTime;

}
