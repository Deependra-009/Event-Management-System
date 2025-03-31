package com.system.event_management.service.impl;

import com.system.event_management.core.UserConstants;
import com.system.event_management.entity.UserEntity;
import com.system.event_management.exception.UserException;
import com.system.event_management.model.userbeans.login.LoginResponseBean;
import com.system.event_management.model.userbeans.user.UserDataBean;
import com.system.event_management.model.userbeans.user.UserRequestBean;
import com.system.event_management.model.userbeans.user.UserResponseBean;
import com.system.event_management.repository.UserRepository;
import com.system.event_management.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponseBean<?> createUser(UserRequestBean userRequestBean) throws UserException {

        if(this.userRepository.isUserAlreadyExist(userRequestBean.getUsername())>0){
            throw new UserException(String.format(UserConstants.USER_ALREADY_EXISTS,userRequestBean.getUsername()), HttpStatus.CONFLICT);
        }

        UserEntity userEntity=this.userRepository.save(
                UserEntity.builder()
                        .fullName(userRequestBean.getFullName())
                        .username(userRequestBean.getUsername())
                        .password(userRequestBean.getPassword())
                        .build()
        );

        UserDataBean userDataBean=new UserDataBean();

        BeanUtils.copyProperties(userEntity,userDataBean);

        return UserResponseBean.builder()
                .status(true)
                .message(UserConstants.USER_REGISTER_SUCCESS)
                .data(userDataBean)
                .build();

    }

    @Override
    public LoginResponseBean<?> loginUser(UserRequestBean userRequestBean) {
        return null;
    }
}
