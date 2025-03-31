package com.system.event_management.controller;

import com.system.event_management.core.EventConstants;
import com.system.event_management.exception.EventNotFoundException;
import com.system.event_management.exception.InvalidEventIDException;
import com.system.event_management.exception.UserException;
import com.system.event_management.model.eventbeans.EventRequestBean;
import com.system.event_management.model.eventbeans.EventResponseBean;
import com.system.event_management.model.rsvpbeans.RSVPRequestBean;
import com.system.event_management.model.rsvpbeans.RSVPResponseBean;
import com.system.event_management.service.EventManagementService;
import com.system.event_management.service.RSVPService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
@Tag(name = "Events API", description = "Endpoints for managing events and RSVP actions")
public class EventController {

    @Autowired
    private EventManagementService eventManagementService;

    @Autowired
    private RSVPService rsvpService;

    @GetMapping
    @Operation(summary = "Retrieve all events", description = "Fetches a paginated list of all events.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the events"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<EventResponseBean<?>> getAllEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return new ResponseEntity<>(eventManagementService.getAllEvents(page, limit), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create a new event", description = "Creates a new event with the given details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Event created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    public ResponseEntity<EventResponseBean<?>> createEvent(@RequestBody EventRequestBean eventRequestBean) {
        return new ResponseEntity<>(eventManagementService.createEvent(eventRequestBean), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing event", description = "Updates the details of an event by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event updated successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "400", description = "Invalid event ID format")
    })
    public ResponseEntity<EventResponseBean<?>> updateEvent(
            @RequestBody EventRequestBean eventRequestBean,
            @PathVariable("id") String id
    ) throws EventNotFoundException, InvalidEventIDException {
        try {
            return new ResponseEntity<>(eventManagementService.updateEvent(eventRequestBean, Long.parseLong(id)), HttpStatus.OK);
        } catch (NumberFormatException e) {
            throw new InvalidEventIDException(EventConstants.EVENT_ID_INVALID);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an event", description = "Deletes an event by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "400", description = "Invalid event ID format")
    })
    public ResponseEntity<EventResponseBean<?>> deleteEvent(@PathVariable("id") String id)
            throws EventNotFoundException, InvalidEventIDException {
        try {
            return new ResponseEntity<>(eventManagementService.deleteEvent(Long.parseLong(id)), HttpStatus.OK);
        } catch (NumberFormatException e) {
            throw new InvalidEventIDException(EventConstants.EVENT_ID_INVALID);
        }
    }

    @PostMapping("/{eventId}/rsvp")
    @Operation(summary = "RSVP to an event", description = "Allows a user to RSVP for an event.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "RSVP successful"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "400", description = "Invalid RSVP request")
    })
    public ResponseEntity<RSVPResponseBean<?>> rsvpToEvent(
            @PathVariable Long eventId,
            @RequestBody RSVPRequestBean rsvpRequestBean
    ) throws EventNotFoundException, UserException {
        return new ResponseEntity<>(rsvpService.registerRSVP(eventId, rsvpRequestBean), HttpStatus.OK);
    }
}
