package com.system.event_management.service.impl;

import com.system.event_management.core.EventConstants;
import com.system.event_management.entity.EventEntity;
import com.system.event_management.exception.EventNotFoundException;
import com.system.event_management.model.eventbeans.EventRequestBean;
import com.system.event_management.model.eventbeans.EventDataBean;
import com.system.event_management.model.eventbeans.EventResponseBean;
import com.system.event_management.model.rsvpbeans.RSVPData;
import com.system.event_management.repository.EventRepository;
import com.system.event_management.service.EventManagementService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventManagementServiceImpl implements EventManagementService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public EventResponseBean<?> getAllEvents(int page, int limit) {

        Page<EventEntity> eventPage = eventRepository.findAll(PageRequest.of(page, limit));

        return EventResponseBean.builder()
                .status(true)
                .data(mappedAllEventsDataIntoBean(eventPage))
                .build();
    }

    @Override
    public EventResponseBean<?> createEvent(EventRequestBean eventRequestBean) {

        EventEntity eventEntity=this.eventRepository.save(
                EventEntity.builder()
                        .eventName(eventRequestBean.getEventName())
                        .eventDateTime(eventRequestBean.getEventDateTime())
                        .eventLocation(eventRequestBean.getEventLocation())
                        .build()
        );

        EventDataBean eventDataBean =new EventDataBean();

        BeanUtils.copyProperties(eventEntity, eventDataBean);

        return EventResponseBean.builder()
                .status(true)
                .message(EventConstants.EVENT_CREATE_SUCCESS)
                .data(eventDataBean)
                .build();
    }

    @Override
    public EventResponseBean<?> updateEvent(EventRequestBean eventRequestBean, Long id) throws EventNotFoundException {

        EventEntity eventEntity = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(String.format(EventConstants.EVENT_NOT_FOUND, id)));

        BeanUtils.copyProperties(eventRequestBean,eventEntity);
        eventEntity.setEventId(id);

        eventEntity = this.eventRepository.save(eventEntity);

        EventDataBean eventDataBean =new EventDataBean();

        BeanUtils.copyProperties(eventEntity, eventDataBean);

        return EventResponseBean.builder()
                .status(true)
                .message(EventConstants.EVENT_UPDATE_SUCCESS)
                .data(eventDataBean)
                .build();
    }

    @Override
    public EventResponseBean<?> deleteEvent(Long id) throws EventNotFoundException {

        if(!this.eventRepository.existsById(id)){
            throw new EventNotFoundException(String.format(EventConstants.EVENT_NOT_FOUND, id));
        }
        this.eventRepository.deleteById(id);

        return EventResponseBean.builder()
                .status(true)
                .message(EventConstants.EVENT_DELETE_SUCCESS)
                .build();
    }

    private List<EventDataBean> mappedAllEventsDataIntoBean(Page<EventEntity> eventPage){

        List<EventDataBean> eventDataBeanList=new ArrayList<>();

        eventPage.getContent().forEach((event)->{

            EventDataBean eventDataBean=EventDataBean
                    .builder()
                    .eventId(event.getEventId())
                    .eventName(event.getEventName())
                    .eventDateTime(event.getEventDateTime())
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


}
