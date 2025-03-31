package com.system.event_management.controller;

import com.system.event_management.model.userbeans.user.UserDataBean;
import com.system.event_management.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin API", description = "Endpoints for admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/get-all-user")
    @Operation(summary = "Get All User", description = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All user successfully")
    })
    public ResponseEntity<List<UserDataBean>> getAllUser(){
        return new ResponseEntity<>(this.userService.getAllUser(), HttpStatus.OK);
    }

}
