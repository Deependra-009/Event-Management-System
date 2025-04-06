package com.system.event_management.model.userbeans.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.system.event_management.model.eventbeans.EventDataBean;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDataBean {

    private Long userID;
    private String fullName;
    private String username;
    private List<String> roles;
    private List<EventDataBean> events;


}
