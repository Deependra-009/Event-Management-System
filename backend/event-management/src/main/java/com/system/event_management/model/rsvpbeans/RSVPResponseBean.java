package com.system.event_management.model.rsvpbeans;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class RSVPResponseBean<T> {

    private boolean status;
    private String message;
    private T data;

}
