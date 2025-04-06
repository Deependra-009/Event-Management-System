package com.system.event_management.service.impl;

import com.system.event_management.core.messages.EventMessages;
import com.system.event_management.core.messages.RSVPMessages;
import com.system.event_management.core.messages.UserMessages;
import com.system.event_management.entity.EventEntity;
import com.system.event_management.entity.RSVPEntity;
import com.system.event_management.entity.UserEntity;
import com.system.event_management.enums.RedisEnums;
import com.system.event_management.exception.EventNotFoundException;
import com.system.event_management.exception.UserException;
import com.system.event_management.model.rsvpbeans.RSVPData;
import com.system.event_management.model.rsvpbeans.RSVPRequestBean;
import com.system.event_management.model.rsvpbeans.RSVPResponseBean;
import com.system.event_management.repository.EventRepository;
import com.system.event_management.repository.RSVPRepository;
import com.system.event_management.repository.UserRepository;
import com.system.event_management.service.RSVPService;
import com.system.event_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class RSVPServiceImpl implements RSVPService {

    @Autowired
    private RSVPRepository rsvpRepository;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EventRepository eventRepository;

    @Override
    public RSVPResponseBean<?> registerRSVP(Long eventId, RSVPRequestBean rsvpRequestBean) throws UserException, EventNotFoundException {

        Long userID=this.userService.getUserData();
        String username= SecurityContextHolder.getContext().getAuthentication().getName();

        this.redisService.deleteValue(RedisEnums.GET_ALL_EVENTS.name());

        UserEntity user = userRepository.findById(userID)
                .orElseThrow(() -> new UserException(String.format(UserMessages.USER_NOT_FOUND,username), HttpStatus.NOT_FOUND));

        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(String.format(EventMessages.EVENT_NOT_FOUND, eventId)));

        if (rsvpRepository.existsByUserEntityUserIDAndEventEntityEventId(userID, eventId)) {
            throw new UserException(String.format(RSVPMessages.RSVP_ALREADY_EXISTS,username,eventId), HttpStatus.CONFLICT);
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
                .message(String.format(RSVPMessages.RSVP_SUCCESS,eventId,username))
                .data(RSVPData.builder().username(rsvp.getUserEntity().getUsername()).attending(rsvpRequestBean.isAttending()).build())
                .build();
    }

    @Override
    public RSVPResponseBean<?> updateRSVP(Long eventID, RSVPRequestBean rsvpRequestBean) throws UserException {
        this.redisService.deleteValue(RedisEnums.GET_ALL_EVENTS.name());

        Long userID=this.userService.getUserData();
        String username= SecurityContextHolder.getContext().getAuthentication().getName();

        RSVPEntity rsvpEntity=this.rsvpRepository.findByUserEntityUserIDAndEventEntityEventId(userID,eventID);
        if(rsvpEntity==null){
            throw new UserException(RSVPMessages.RSVP_NOT_REGISTERED, HttpStatus.CONFLICT);
        }
        rsvpEntity.setAttending(rsvpRequestBean.isAttending());
        RSVPEntity rsvp = this.rsvpRepository.save(rsvpEntity);

        return RSVPResponseBean.builder()
                .status(true)
                .message(RSVPMessages.RSVP_UPDATE_SUCCESSFULLY)
                .data(RSVPData.builder().username(rsvp.getUserEntity().getUsername()).attending(rsvpRequestBean.isAttending()).build())
                .build();
    }


}
