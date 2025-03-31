package com.system.event_management.service.impl;

import com.system.event_management.core.UserConstants;
import com.system.event_management.entity.RolesEntity;
import com.system.event_management.entity.UserEntity;
import com.system.event_management.exception.UserException;
import com.system.event_management.jwt.JwtHelper;
import com.system.event_management.model.userbeans.login.LoginData;
import com.system.event_management.model.userbeans.login.LoginRequestBean;
import com.system.event_management.model.userbeans.login.LoginResponseBean;
import com.system.event_management.model.userbeans.user.UserDataBean;
import com.system.event_management.model.userbeans.user.UserRequestBean;
import com.system.event_management.model.userbeans.user.UserResponseBean;
import com.system.event_management.repository.RolesRepository;
import com.system.event_management.repository.UserRepository;
import com.system.event_management.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtHelper jwtHelper;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserResponseBean<?> createUser(UserRequestBean userRequestBean) throws UserException {

        userRequestBean.setPassword(passwordEncoder.encode(userRequestBean.getPassword()));

        if(this.userRepository.isUserAlreadyExist(userRequestBean.getUsername())>0){
            throw new UserException(String.format(UserConstants.USER_ALREADY_EXISTS,userRequestBean.getUsername()), HttpStatus.CONFLICT);
        }

        // Save User

        UserEntity userEntity=this.userRepository.save(
                UserEntity.builder()
                        .fullName(userRequestBean.getFullName())
                        .username(userRequestBean.getUsername())
                        .password(userRequestBean.getPassword())
                        .build()
        );

        // Save Roles

        List<RolesEntity> rolesEntityList=new ArrayList<>();

        UserEntity finalUserEntity = userEntity;
        userRequestBean.getRoles().stream().forEach(userRole->{
            RolesEntity roles = this.rolesRepository.save(RolesEntity
                    .builder().role(userRole).userEntity(finalUserEntity).build());
            rolesEntityList.add(roles);
        });
        userEntity.setRoles(rolesEntityList);
        userEntity=this.userRepository.save(userEntity);

        UserDataBean userDataBean=new UserDataBean();

        BeanUtils.copyProperties(userEntity,userDataBean);

        return UserResponseBean.builder()
                .status(true)
                .message(UserConstants.USER_REGISTER_SUCCESS)
                .data(userDataBean)
                .build();

    }

    @Override
    public LoginResponseBean<?> loginUser(LoginRequestBean loginRequestBean) throws UserException {

        log.info("Username : "+ loginRequestBean.getPassword());

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestBean.getUsername(), loginRequestBean.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestBean.getUsername());
            String jwt = jwtHelper.generateToken(userDetails);
            return LoginResponseBean.builder()
                    .status(true)
                    .message(UserConstants.LOGIN_SUCCESS)
                    .data(new LoginData(userDetails.getUsername(),jwt))
                    .build();
        }
        catch (Exception e){
            log.info(e.getMessage());
//            return new LoginResponseBean<>(false,UserConstants.INVALID_CREDENTIALS,null);
            throw new UserException(UserConstants.INVALID_CREDENTIALS,HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public List<UserDataBean> getAllUser() {
        List<UserEntity> userEntityList = this.userRepository.findAll();



        List<UserDataBean> userResponseBeanList=new ArrayList<>();

        userEntityList.forEach((user)->{

            List<String> roles=new ArrayList<>();

            UserDataBean userDataBean=new UserDataBean();

            BeanUtils.copyProperties(user,userDataBean);

            user.getRoles().forEach((role)->roles.add(role.getRole()));

            userDataBean.setRoles(roles);

            userResponseBeanList.add(userDataBean);

        });

        return userResponseBeanList;
    }


}
