package com.gym.controller;

import com.gym.dto.EjercicioRequest;
import com.gym.dto.MensajeResponse;
import com.gym.model.Ejercicio;
import com.gym.service.EjercicioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ejercicios")
@Tag(name = "Ejercicios", description = "CRUD de ejercicios")
public class EjercicioController {

    private final EjercicioService service;

    public EjercicioController(EjercicioService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todos los ejercicios")
    public List<Ejercicio> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un ejercicio por id")
    public Ejercicio obtener(@PathVariable String id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear un ejercicio")
    public Ejercicio crear(@RequestBody EjercicioRequest req) {
        return service.crear(req);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un ejercicio")
    public ResponseEntity<Ejercicio> actualizar(@PathVariable String id, @RequestBody EjercicioRequest req) {
        return ResponseEntity.ok(service.actualizar(id, req));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un ejercicio")
    public MensajeResponse eliminar(@PathVariable String id) {
        service.eliminar(id);
        return new MensajeResponse("Ejercicio eliminado");
    }
}
