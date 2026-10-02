package com.gym.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@Tag(name = "Root", description = "Informacion de la API")
public class RootController {

    @GetMapping("/")
    @Operation(summary = "Informacion de la API y endpoints disponibles")
    public Map<String, Object> root() {
        Map<String, Object> endpoints = new LinkedHashMap<>();
        endpoints.put("ejercicios", "/ejercicios");
        endpoints.put("rutinas", "/rutinas");
        endpoints.put("comidas", "/comidas");

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("mensaje", "API Gym Microservice");
        response.put("endpoints", endpoints);
        return response;
    }
}
