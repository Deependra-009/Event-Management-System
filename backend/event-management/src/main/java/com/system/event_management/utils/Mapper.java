package com.system.event_management.utils;

import com.system.event_management.entity.EventEntity;
import com.system.event_management.entity.RolesEntity;
import com.system.event_management.entity.UserEntity;
import com.system.event_management.model.eventbeans.EventDataBean;
import com.system.event_management.model.rsvpbeans.RSVPData;
import com.system.event_management.model.userbeans.user.UserDataBean;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

    public static List<EventDataBean> mappedAllEventsDataIntoBean(List<EventEntity> eventEntityList,String getByParticularUser) {
        return eventEntityList.stream()
                .map(event -> {
                    EventDataBean eventDataBean = new EventDataBean();
                    BeanUtils.copyProperties(event, eventDataBean);

                    if(!getByParticularUser.equals("USER")){

                        eventDataBean.setCreatedBy(
                                UserDataBean.builder()
                                        .username(event.getUserEntity().getUsername())
                                        .build()
                        );
                    }

                    List<RSVPData> usersList = event.getRsvps().stream()
                            .map(user -> RSVPData.builder()
                                    .username(user.getUserEntity().getUsername())
                                    .attending(user.isAttending())
                                    .build())
                            .collect(Collectors.toList());

                    eventDataBean.setUsers(
                            usersList.isEmpty() || (getByParticularUser.equals("PUBLIC"))
                                    ?null:usersList);

                    return eventDataBean;
                })
                .collect(Collectors.toList());
    }


    public static List<UserDataBean> mappedAllUsersDataIntoBean(List<UserEntity> userEntityList) {
        return userEntityList.stream()
                .map(user -> {
                    UserDataBean userDataBean = new UserDataBean();
                    BeanUtils.copyProperties(user, userDataBean);
                    userDataBean.setRoles(mappedRolesIntoResponseBean(user));
                    return userDataBean;
                })
                .collect(Collectors.toList());
    }


    private static List<String> mappedRolesIntoResponseBean(UserEntity user){
        return user.getRoles().stream().map(RolesEntity::getRole).toList();
    }

}
