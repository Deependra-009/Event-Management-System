package com.system.event_management.controller;

import com.system.event_management.model.eventbeans.EventResponseBean;
import com.system.event_management.service.EventManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private EventManagementService eventManagementService;

    @GetMapping("/get-all-events")
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
        return new ResponseEntity<>(eventManagementService.getAllEvents(page, limit,"PUBLIC"), HttpStatus.OK);
    }

}
