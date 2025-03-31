package com.system.event_management.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.event_management.core.UserConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException authException) throws IOException, ServletException {
        // Get the stored exception message
        Object exceptionMessage = request.getAttribute("exception");

        response.setStatus(response.getStatus()); // Preserve the status set in the filter
        response.setContentType("application/json");

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("error", response.getStatus() == 401 ? "Unauthorized" : "Error");
        errorDetails.put("message", exceptionMessage != null ? exceptionMessage.toString() : "Authentication Failed");
        errorDetails.put("path", request.getRequestURI());

        // Convert Map to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(errorDetails);

        response.getWriter().write(jsonResponse);
    }
}
