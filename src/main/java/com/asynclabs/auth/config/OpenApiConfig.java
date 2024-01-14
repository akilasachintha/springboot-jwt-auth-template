package com.asynclabs.auth.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "OBD API Documentation",
                version = "1.0.0",
                description = "OBD  Open API Documentation",
                contact = @io.swagger.v3.oas.annotations.info.Contact(
                        name = "Zynovatex",
                        email = "info@zynovatex.com")
        ),
        servers = {
                @Server(
                        description = "Local Server",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Production Server",
                        url = "https://obd.zynovatex.com"
                ),
                @Server(
                        description = "QA Server",
                        url = "https://dev.obd.zynovatex.com"
                )
        },
        security = {
                @SecurityRequirement(name = "bearerAuth")
        }
)

@SecurityScheme(
        name = "bearerAuth",
        description = "JWT authentication support",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
