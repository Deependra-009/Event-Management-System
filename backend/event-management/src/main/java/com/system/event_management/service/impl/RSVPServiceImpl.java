package com.system.event_management.service.impl;

import com.system.event_management.core.EventConstants;
import com.system.event_management.core.RSVPConstants;
import com.system.event_management.core.UserConstants;
import com.system.event_management.entity.EventEntity;
import com.system.event_management.entity.RSVPEntity;
import com.system.event_management.entity.UserEntity;
import com.system.event_management.exception.EventNotFoundException;
import com.system.event_management.exception.UserException;
import com.system.event_management.model.rsvpbeans.RSVPData;
import com.system.event_management.model.rsvpbeans.RSVPRequestBean;
import com.system.event_management.model.rsvpbeans.RSVPResponseBean;
import com.system.event_management.repository.EventRepository;
import com.system.event_management.repository.RSVPRepository;
import com.system.event_management.repository.UserRepository;
import com.system.event_management.service.RSVPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class RSVPServiceImpl implements RSVPService {

    @Autowired
    private RSVPRepository rsvpRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Override
    public RSVPResponseBean<?> registerRSVP(Long eventId, RSVPRequestBean rsvpRequestBean) throws UserException, EventNotFoundException {

        UserEntity user = userRepository.findById(rsvpRequestBean.getUserID())
                .orElseThrow(() -> new UserException(String.format(UserConstants.USER_NOT_FOUND,rsvpRequestBean.getUserID()), HttpStatus.NOT_FOUND));

        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(String.format(EventConstants.EVENT_NOT_FOUND, eventId)));

        if (rsvpRepository.existsByUserEntityUserIDAndEventEntityEventId(rsvpRequestBean.getUserID(), eventId)) {
            throw new UserException(String.format(RSVPConstants.RSVP_ALREADY_EXISTS,rsvpRequestBean.getUserID(),eventId), HttpStatus.CONFLICT);
        }

        RSVPEntity rsvp = this.rsvpRepository.save(
                RSVPEntity.builder()
                        .attending(rsvpRequestBean.isAttending())
                        .eventEntity(event)
                        .userEntity(user)
                        .build()
        );

        return RSVPResponseBean.builder()
                .status(true)
                .message(String.format(RSVPConstants.RSVP_SUCCESS,rsvpRequestBean.getUserID(),eventId))
                .data(RSVPData.builder().userID(rsvp.getUserEntity().getUserID()).attending(rsvpRequestBean.isAttending()).build())
                .build();
    }

}
