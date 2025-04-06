package com.system.event_management.model.eventbeans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.system.event_management.entity.RSVPEntity;
import com.system.event_management.model.rsvpbeans.RSVPData;
import com.system.event_management.model.userbeans.user.UserDataBean;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventDataBean {

    private Long eventId;
    private String eventName;
    private String eventLocation;
    private LocalDateTime eventDateTime;
    private LocalDateTime postedAt;
    private UserDataBean createdBy;
    private List<RSVPData> users;

}
