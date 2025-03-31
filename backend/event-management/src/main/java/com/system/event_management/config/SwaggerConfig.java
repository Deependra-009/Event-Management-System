package com.system.event_management.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class SwaggerConfig {

    /**
     * Configures the OpenAPI specification for Swagger UI.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // API Information
                .info(apiInfo())

                // Server Configurations
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Development Server"),
                        new Server().url("https://api.example.com").description("Production Server")
                ))

                // Security Scheme (JWT Authentication)
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", securityScheme())
                        .responses(globalResponses())
                );
    }

    /**
     * Defines API metadata like title, version, description, and contact details.
     */
    private Info apiInfo() {
        return new Info()
                .title("Event Management System API")
                .version("1.0")
                .description("API documentation for Event Management System")
                .contact(new Contact()
                        .name("Developer Support")
                        .email("support@example.com")
                        .url("https://example.com"))
                .license(new License()
                        .name("Apache 2.0")
                        .url("https://www.apache.org/licenses/LICENSE-2.0.html"));
    }

    /**
     * Defines JWT Security Schema for API Authentication.
     */
    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .name("bearerAuth")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("Enter your JWT token here.");
    }

    /**
     * Defines Global API Responses for all endpoints.
     */
    private Map<String, ApiResponse> globalResponses() {
        return Map.of(
                "400", new ApiResponse().description("Bad Request - Invalid input"),
                "401", new ApiResponse().description("Unauthorized - Token required"),
                "403", new ApiResponse().description("Forbidden - Access denied"),
                "404", new ApiResponse().description("Not Found - Resource does not exist"),
                "500", new ApiResponse().description("Internal Server Error - Contact support")
        );
    }
}
