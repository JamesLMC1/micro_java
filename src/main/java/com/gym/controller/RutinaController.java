package com.gym.controller;

import com.gym.dto.MensajeResponse;
import com.gym.dto.RutinaRequest;
import com.gym.model.Rutina;
import com.gym.model.RutinaDetalle;
import com.gym.service.RutinaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rutinas")
@Tag(name = "Rutinas", description = "CRUD de rutinas y sus ejercicios")
public class RutinaController {

    private final RutinaService service;

    public RutinaController(RutinaService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todas las rutinas")
    public List<Rutina> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una rutina con sus ejercicios")
    public RutinaDetalle obtener(@PathVariable String id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear una rutina")
    public Rutina crear(@RequestBody RutinaRequest req) {
        return service.crear(req);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una rutina")
    public ResponseEntity<Rutina> actualizar(@PathVariable String id, @RequestBody RutinaRequest req) {
        return ResponseEntity.ok(service.actualizar(id, req));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una rutina")
    public MensajeResponse eliminar(@PathVariable String id) {
        service.eliminar(id);
        return new MensajeResponse("Rutina eliminada");
    }
}
