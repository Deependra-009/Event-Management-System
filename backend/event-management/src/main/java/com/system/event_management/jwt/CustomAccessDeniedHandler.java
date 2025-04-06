package com.system.event_management.jwt;

import com.system.event_management.core.messages.UserMessages;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("error", "Forbidden");
        errorDetails.put("message", UserMessages.UNAUTHORIZED_ACCESS);
        errorDetails.put("path", request.getRequestURI());

        // Convert Map to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(errorDetails);

        response.getWriter().write(jsonResponse);
    }
}

