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

@Service
@Slf4j
public class EventManagementServiceImpl implements EventManagementService {

    @Autowired private RedisService redisService;
    @Autowired private UserService userService;
    @Autowired private EventRepository eventRepository;

    private String getLoggedInUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private Long getCurrentUserId() {
        return userService.getUserData(); // Assuming it returns userId
    }

    @Override
    public EventResponseBean<?> getAllEvents(int page, int limit, String type) {
        String cacheKey = RedisEnums.GET_ALL_EVENTS.name();
        this.redisService.deleteValue(cacheKey);
        List<EventEntity> cachedEvents = redisService.getValue(cacheKey, new TypeReference<>() {});

        if (cachedEvents != null) {
            return EventResponseBean.builder().status(true).data(cachedEvents).build();
        }

        Page<EventEntity> eventsPage = eventRepository.findAll(PageRequest.of(page, limit));
        List<EventDataBean> eventData = Mapper.mappedAllEventsDataIntoBean(eventsPage.getContent(), type);

        redisService.setValue(cacheKey, eventData, 600);

        return EventResponseBean.builder().status(true).data(eventData).build();
    }

    @Override
    public EventResponseBean<?> createEvent(EventRequestBean request) {
        String username = getLoggedInUsername();
        String cacheKey = RedisEnums.GET_ALL_EVENTS_OF_PARTICULAR_USERS.name() + "_" + username;

        redisService.deleteValue(RedisEnums.GET_ALL_EVENTS.name());
        redisService.deleteValue(cacheKey);

        EventEntity event = EventEntity.builder()
                .eventName(request.getEventName())
                .eventDateTime(request.getEventDateTime())
                .eventLocation(request.getEventLocation())
                .userEntity(UserEntity.builder().userID(getCurrentUserId()).build())
                .postedAt(LocalDateTime.now())
                .build();

        event = eventRepository.save(event);

        EventDataBean dataBean = new EventDataBean();
        BeanUtils.copyProperties(event, dataBean);
        dataBean.setCreatedBy(UserDataBean.builder().username(getLoggedInUsername()).build());

        return EventResponseBean.builder()
                .status(true)
                .message(EventMessages.EVENT_CREATE_SUCCESS)
                .data(dataBean)
                .build();
    }

    @Override
    public EventResponseBean<?> updateEvent(EventRequestBean request, Long id) throws EventNotFoundException {
        String username = getLoggedInUsername();
        String cacheKey = RedisEnums.GET_ALL_EVENTS_OF_PARTICULAR_USERS.name() + "_" + username;

        redisService.deleteValue(RedisEnums.GET_ALL_EVENTS.name());
        redisService.deleteValue(cacheKey);

        EventEntity event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(String.format(EventMessages.EVENT_NOT_FOUND, id)));

        BeanUtils.copyProperties(request, event);
        event.setEventId(id);
        event = eventRepository.save(event);

        EventDataBean dataBean = new EventDataBean();
        BeanUtils.copyProperties(event, dataBean);

        return EventResponseBean.builder()
                .status(true)
                .message(EventMessages.EVENT_UPDATE_SUCCESS)
                .data(dataBean)
                .build();
    }

    @Override
    public EventResponseBean<?> deleteEvent(Long id) throws EventNotFoundException {
        String username = getLoggedInUsername();
        String cacheKey = RedisEnums.GET_ALL_EVENTS_OF_PARTICULAR_USERS.name() + "_" + username;

        redisService.deleteValue(RedisEnums.GET_ALL_EVENTS.name());
        redisService.deleteValue(cacheKey);

        if (!eventRepository.existsById(id)) {
            throw new EventNotFoundException(String.format(EventMessages.EVENT_NOT_FOUND, id));
        }

        eventRepository.deleteById(id);

        return EventResponseBean.builder()
                .status(true)
                .message(EventMessages.EVENT_DELETE_SUCCESS)
                .build();
    }

    @Override
    public EventResponseBean<?> getAllEventsOfParticularUser() {
        String username = getLoggedInUsername();
        String cacheKey = RedisEnums.GET_ALL_EVENTS_OF_PARTICULAR_USERS.name() + "_" + username;

        EventResponseBean<?> cachedResponse = redisService.getValue(cacheKey, new TypeReference<>() {});
        if (cachedResponse != null) {
            return cachedResponse;
        }

        List<EventEntity> userEvents = eventRepository.findByUserEntityUserID(getCurrentUserId());
        List<EventDataBean> eventData = Mapper.mappedAllEventsDataIntoBean(userEvents, "USER");

        EventResponseBean<?> response = EventResponseBean.builder()
                .status(true)
                .message("GET ALL EVENTS")
                .data(eventData)
                .build();

        redisService.setValue(cacheKey, response, 600);

        return response;
    }
}
