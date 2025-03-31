package com.system.event_management.model.userbeans.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDataBean {

    private Long userID;
    private String fullName;
    private String username;
    private String password;


}
