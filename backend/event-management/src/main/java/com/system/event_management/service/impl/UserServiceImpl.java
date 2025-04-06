package com.system.event_management.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.system.event_management.core.messages.UserMessages;
import com.system.event_management.entity.RolesEntity;
import com.system.event_management.entity.UserEntity;
import com.system.event_management.enums.RedisEnums;
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
import com.system.event_management.utils.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisService redisService;

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

        this.redisService.deleteValue(RedisEnums.GET_ALL_USERS.name());

        userRequestBean.setPassword(passwordEncoder.encode(userRequestBean.getPassword()));

        if(this.userRepository.isUserAlreadyExist(userRequestBean.getUsername())>0){
            throw new UserException(String.format(UserMessages.USER_ALREADY_EXISTS,userRequestBean.getUsername()), HttpStatus.CONFLICT);
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

        Set<RolesEntity> rolesEntityList=new HashSet<>();

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
                .message(UserMessages.USER_REGISTER_SUCCESS)
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
                    .message(UserMessages.LOGIN_SUCCESS)
                    .data(new LoginData(userDetails.getUsername(),jwt))
                    .build();
        }
        catch (Exception e){
            log.info(e.getMessage());
//            return new LoginResponseBean<>(false,UserConstants.INVALID_CREDENTIALS,null);
            throw new UserException(UserMessages.INVALID_CREDENTIALS,HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public List<UserDataBean> getAllUser() {
        List<UserDataBean> cacheData=this.redisService.getValue(RedisEnums.GET_ALL_USERS.name(), new TypeReference<List<UserDataBean>>(){});
        if(cacheData!=null){
            return cacheData;
        }
        else{
            List<UserEntity> userEntityList = this.userRepository.fetchUserWithRolesAndRsvps();
            List<UserDataBean> userDataBeans = Mapper.mappedAllUsersDataIntoBean(userEntityList);
            this.redisService.setValue(RedisEnums.GET_ALL_USERS.name(), userDataBeans,600);
            return userDataBeans;
        }
    }

    @Override
    public Long getUserData(){
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        Long userID=this.redisService.getValue(RedisEnums.GET_PARTICULAR_USER.name()+"_"+username, new TypeReference<Long>(){});
        if(userID!=null) return userID;
        else{
            userID=this.userRepository.fetchUserIdByUsername(username);
            this.redisService.setValue(RedisEnums.GET_PARTICULAR_USER.name()+"_"+username,userID,600);
            return userID;
        }

    }


}
