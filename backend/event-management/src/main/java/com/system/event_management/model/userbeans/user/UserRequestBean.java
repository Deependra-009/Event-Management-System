package com.system.event_management.model.userbeans.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestBean {

    private Long userID;
    private String fullName;
    private String username;
    private String password;
    private List<String> roles;
}
