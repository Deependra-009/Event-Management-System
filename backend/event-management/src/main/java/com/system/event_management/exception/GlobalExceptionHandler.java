package com.system.event_management.exception;

import com.system.event_management.model.ExceptionBean;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ExceptionBean> handleEventNotFoundException(EventNotFoundException ex) {
        return new ResponseEntity<>(new ExceptionBean(ex.getMessage()),HttpStatus.NOT_FOUND );
    }

    @ExceptionHandler(InvalidEventIDException.class)
    public ResponseEntity<ExceptionBean> handleInvalidIDException(InvalidEventIDException ex) {
        return new ResponseEntity<>(new ExceptionBean(ex.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ExceptionBean> handleUserException(UserException ex) {
        return new ResponseEntity<>(new ExceptionBean(ex.getMessage()),ex.getHttpStatus());
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<ExceptionBean> handleJwtException(JwtAuthenticationException ex) {
        return new ResponseEntity<>(new ExceptionBean(ex.getMessage()),ex.getHttpStatus());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionBean> handleGenericException(Exception ex) {
        return new ResponseEntity<>(new ExceptionBean(ex.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR );
    }

//    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message) {
//        Map<String, Object> errorDetails = new HashMap<>();
//        errorDetails.put("status", status.value());
//        errorDetails.put("error", status.getReasonPhrase());
//        errorDetails.put("message", message);
//        errorDetails.put("timestamp", LocalDateTime.now());
//
//        return new ResponseEntity<>(errorDetails, status);
//    }


}
