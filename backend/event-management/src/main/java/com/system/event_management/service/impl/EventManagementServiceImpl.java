package com.system.event_management.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.system.event_management.core.messages.EventMessages;
import com.system.event_management.entity.EventEntity;
import com.system.event_management.entity.UserEntity;
import com.system.event_management.enums.RedisEnums;
import com.system.event_management.exception.EventNotFoundException;
import com.system.event_management.model.eventbeans.EventRequestBean;
import com.system.event_management.model.eventbeans.EventDataBean;
import com.system.event_management.model.eventbeans.EventResponseBean;
import com.system.event_management.model.userbeans.user.UserDataBean;
import com.system.event_management.repository.EventRepository;
import com.system.event_management.repository.UserRepository;
import com.system.event_management.service.EventManagementService;
import com.system.event_management.service.UserService;
import com.system.event_management.utils.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class EventManagementServiceImpl implements EventManagementService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserService userService;

    @Autowired
    private EventRepository eventRepository;

    @Override
    public EventResponseBean<?> getAllEvents(int page, int limit) {

        List<EventEntity> cacheData = redisService.getValue(RedisEnums.GET_ALL_EVENTS.name(), new TypeReference<List<EventEntity>>() {});


        if(cacheData!=null){
            return EventResponseBean.builder()
                    .status(true)
                    .data(cacheData)
                    .build();
        }
        else{
            Page<EventEntity> eventPage = eventRepository.findAll(PageRequest.of(page, limit));

            EventResponseBean<?> eventResponseBean=EventResponseBean.builder()
                    .status(true)
                    .data(Mapper.mappedAllEventsDataIntoBean(eventPage.getContent()))
                    .build();

//            this.redisService.setValue(RedisEnums.GET_ALL_EVENTS.name(),eventResponseBean.getData(),600);
            return eventResponseBean;
        }
    }

    @Override
    public EventResponseBean<?> createEvent(EventRequestBean eventRequestBean) {

        this.redisService.deleteValue(RedisEnums.GET_ALL_EVENTS.name());

        EventEntity eventEntity=this.eventRepository.save(
                EventEntity.builder()
                        .eventName(eventRequestBean.getEventName())
                        .eventDateTime(eventRequestBean.getEventDateTime())
                        .eventLocation(eventRequestBean.getEventLocation())
                        .userEntity(UserEntity.builder().userID(this.userService.getUserData()).build())
                        .postedAt(LocalDateTime.now())
                        .build()
        );

        EventDataBean eventDataBean =new EventDataBean();

        BeanUtils.copyProperties(eventEntity, eventDataBean);

        eventDataBean.setCreatedBy(UserDataBean.builder().username(SecurityContextHolder.getContext().getAuthentication().getName()).build());

        return EventResponseBean.builder()
                .status(true)
                .message(EventMessages.EVENT_CREATE_SUCCESS)
                .data(eventDataBean)
                .build();
    }

    @Override
    public EventResponseBean<?> updateEvent(EventRequestBean eventRequestBean, Long id) throws EventNotFoundException {

//        this.redisService.deleteValue(RedisEnums.GET_ALL_EVENTS.name());

        EventEntity eventEntity = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(String.format(EventMessages.EVENT_NOT_FOUND, id)));

        BeanUtils.copyProperties(eventRequestBean,eventEntity);
        eventEntity.setEventId(id);

        eventEntity = this.eventRepository.save(eventEntity);

        EventDataBean eventDataBean =new EventDataBean();

        BeanUtils.copyProperties(eventEntity, eventDataBean);

        return EventResponseBean.builder()
                .status(true)
                .message(EventMessages.EVENT_UPDATE_SUCCESS)
                .data(eventDataBean)
                .build();
    }

    @Override
    public EventResponseBean<?> deleteEvent(Long id) throws EventNotFoundException {

//        this.redisService.deleteValue(RedisEnums.GET_ALL_EVENTS.name());

        if(!this.eventRepository.existsById(id)){
            throw new EventNotFoundException(String.format(EventMessages.EVENT_NOT_FOUND, id));
        }
        this.eventRepository.deleteById(id);

        return EventResponseBean.builder()
                .status(true)
                .message(EventMessages.EVENT_DELETE_SUCCESS)
                .build();
    }




}
