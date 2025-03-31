package com.system.event_management.model.eventbeans;

import com.system.event_management.entity.RSVPEntity;
import com.system.event_management.model.rsvpbeans.RSVPData;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDataBean {

    private Long eventId;
    private String eventName;
    private String eventLocation;
    private LocalDateTime eventDateTime;
    private List<RSVPData> users;

}
