package com.system.event_management.service;

import com.system.event_management.exception.EventNotFoundException;
import com.system.event_management.exception.UserException;
import com.system.event_management.model.rsvpbeans.RSVPRequestBean;
import com.system.event_management.model.rsvpbeans.RSVPResponseBean;

public interface RSVPService {

    public RSVPResponseBean<?> registerRSVP(Long eventId, RSVPRequestBean rsvpRequestBean) throws UserException, EventNotFoundException;

}
