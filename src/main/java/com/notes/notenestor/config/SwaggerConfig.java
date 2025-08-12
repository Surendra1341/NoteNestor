package com.notes.notenestor.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("NoteNestor API")
                        .description("REST API documentation for NoteNestor Application")
                        .version("1.0.0")
                        .termsOfService("https://github.com/Surendra1341")
                        .contact(new Contact()
                                .name("Surendra1341")
                                .url("https://github.com/Surendra1341")
                                .email("surendrasingh231206@acropolis.in"))
                        .license(new License()
                                .name("NoteNester API License")
                                .url("https://github.com/NoteNester-API-License")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", // match SecurityRequirement
                                new SecurityScheme()
                                        .name("Authorization")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(SecurityScheme.In.HEADER)))
                .servers(List.of(
                        new Server().description("Dev Server").url("http://localhost:8080")
                ));
    }
}
