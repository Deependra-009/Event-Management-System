package com.system.event_management.service;

import com.system.event_management.exception.EventNotFoundException;
import com.system.event_management.model.eventbeans.EventRequestBean;
import com.system.event_management.model.eventbeans.EventResponseBean;


public interface EventManagementService {

    public EventResponseBean<?> getAllEvents(int page, int limit);

    public EventResponseBean<?> createEvent(EventRequestBean eventRequestBean);

    public EventResponseBean<?> updateEvent(EventRequestBean eventRequestBean, Long id) throws EventNotFoundException;

    public EventResponseBean<?> deleteEvent(Long id) throws EventNotFoundException;

}
