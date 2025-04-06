package com.system.event_management.model.userbeans.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRequestBean {

    private Long userID;
    private String fullName;
    private String username;
    private String password;
    private List<String> roles;
}
