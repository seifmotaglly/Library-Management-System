package com.library.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;


@OpenAPIDefinition(info = @Info(title = "Library Management API", description = "API for managing books and patrons in a library"),
                    security = @SecurityRequirement( name = "Authentication"))
@SecurityScheme(name = "Authentication", scheme = "bearer", description = "JWT auth authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
public class OpenApiConfig {

}
