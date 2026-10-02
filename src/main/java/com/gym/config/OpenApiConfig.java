package com.gym.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI gymOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Gym Microservice - Java (Spring Boot)")
                .version("1.0.0")
                .description("API de ejercicios, rutinas y comidas conectada a Supabase vía REST. " +
                        "Mismo contrato que los microservicios en Python, Node y Go."));
    }
}
