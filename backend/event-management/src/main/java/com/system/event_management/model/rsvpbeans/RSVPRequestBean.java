package com.system.event_management.model.rsvpbeans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RSVPRequestBean {

    private Long userID;
    private boolean attending;
}
