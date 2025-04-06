package com.system.event_management.service;

import com.system.event_management.entity.UserEntity;
import com.system.event_management.exception.UserException;
import com.system.event_management.model.userbeans.login.LoginRequestBean;
import com.system.event_management.model.userbeans.login.LoginResponseBean;
import com.system.event_management.model.userbeans.user.UserDataBean;
import com.system.event_management.model.userbeans.user.UserRequestBean;
import com.system.event_management.model.userbeans.user.UserResponseBean;

import java.util.List;

public interface UserService {

    public UserResponseBean<?> createUser(UserRequestBean userRequestBean) throws UserException;

    public LoginResponseBean<?> loginUser(LoginRequestBean loginRequestBean) throws UserException;

    public List<UserDataBean> getAllUser();

    public Long getUserData();

}
