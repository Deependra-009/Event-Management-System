package com.system.event_management.model.rsvpbeans;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RSVPData {

    private Long userID;
    private boolean attending;
}
