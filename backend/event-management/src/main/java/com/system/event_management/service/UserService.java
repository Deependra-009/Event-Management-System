package com.system.event_management.service;

import com.system.event_management.exception.UserException;
import com.system.event_management.model.userbeans.login.LoginResponseBean;
import com.system.event_management.model.userbeans.user.UserRequestBean;
import com.system.event_management.model.userbeans.user.UserResponseBean;

public interface UserService {

    public UserResponseBean<?> createUser(UserRequestBean userRequestBean) throws UserException;

    public LoginResponseBean<?> loginUser(UserRequestBean userRequestBean);

}
