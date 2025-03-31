package com.system.event_management.model.userbeans.user;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDataBean {

    private Long userID;
    private String fullName;
    private String username;
    private List<String> roles;


}
