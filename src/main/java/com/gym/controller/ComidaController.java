package com.gym.controller;

import com.gym.dto.ComidaRequest;
import com.gym.dto.MensajeResponse;
import com.gym.model.Comida;
import com.gym.service.ComidaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comidas")
@Tag(name = "Comidas", description = "CRUD de comidas / dieta")
public class ComidaController {

    private final ComidaService service;

    public ComidaController(ComidaService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todas las comidas")
    public List<Comida> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una comida por id")
    public Comida obtener(@PathVariable String id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear una comida")
    public Comida crear(@RequestBody ComidaRequest req) {
        return service.crear(req);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una comida")
    public ResponseEntity<Comida> actualizar(@PathVariable String id, @RequestBody ComidaRequest req) {
        return ResponseEntity.ok(service.actualizar(id, req));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una comida")
    public MensajeResponse eliminar(@PathVariable String id) {
        service.eliminar(id);
        return new MensajeResponse("Comida eliminada");
    }
}
