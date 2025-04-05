package com.system.event_management.utils;

import com.system.event_management.entity.EventEntity;
import com.system.event_management.entity.UserEntity;
import com.system.event_management.model.eventbeans.EventDataBean;
import com.system.event_management.model.rsvpbeans.RSVPData;
import com.system.event_management.model.userbeans.user.UserDataBean;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Mapper {

    public static List<EventDataBean> mappedAllEventsDataIntoBean(List<EventEntity> eventEntityList){

        List<EventDataBean> eventDataBeanList=new ArrayList<>();

        eventEntityList.forEach((event)->{

            EventDataBean eventDataBean=EventDataBean
                    .builder()
                    .eventId(event.getEventId())
                    .eventName(event.getEventName())
                    .eventDateTime(event.getEventDateTime().toLocalDateTime())
                    .eventLocation(event.getEventLocation())
                    .build();

            List<RSVPData> usersList=new ArrayList<>();

            event.getRsvps().forEach((user)->{
                RSVPData rsvpData=RSVPData
                        .builder()
                        .userID(user.getUserEntity().getUserID())
                        .attending(user.isAttending())
                        .build();
                usersList.add(rsvpData);
            });

            eventDataBean.setUsers(usersList);

            eventDataBeanList.add(eventDataBean);
        });

        return eventDataBeanList;


    }

    public static List<UserDataBean> mappedAllUsersDataIntoBean(List<UserEntity> userEntityList){
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
