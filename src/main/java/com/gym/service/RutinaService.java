package com.gym.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym.config.SupabaseClient;
import com.gym.dto.RutinaRequest;
import com.gym.exception.ApiException;
import com.gym.model.Rutina;
import com.gym.model.RutinaDetalle;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class RutinaService {

    private final SupabaseClient supabase;
    private final ObjectMapper mapper;

    public RutinaService(SupabaseClient supabase, ObjectMapper mapper) {
        this.supabase = supabase;
        this.mapper = mapper;
    }

    public List<Rutina> listar() {
        return supabase.select("rutinas", "select=*").stream()
                .map(row -> mapper.convertValue(row, Rutina.class))
                .toList();
    }

    public RutinaDetalle obtener(String id) {
        List<Map<String, Object>> rutinas = supabase.select("rutinas", "select=*&id=eq." + id);
        if (rutinas.isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Rutina no encontrada");
        }

        List<Map<String, Object>> ejercicios = supabase.select(
                "rutina_ejercicios", "select=*,ejercicios(*)&rutina_id=eq." + id);

        Map<String, Object> detalle = new LinkedHashMap<>(rutinas.get(0));
        detalle.put("ejercicios", ejercicios);
        return mapper.convertValue(detalle, RutinaDetalle.class);
    }

    public Rutina crear(RutinaRequest req) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("nombre", req.nombre);
        body.put("descripcion", req.descripcion);
        body.put("nivel", req.nivel);
        body.put("duracion_dias", req.duracion_dias);
        removeNulls(body);

        List<Map<String, Object>> rows = supabase.insert("rutinas", body);
        return rows.isEmpty() ? null : mapper.convertValue(rows.get(0), Rutina.class);
    }

    public Rutina actualizar(String id, RutinaRequest req) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("nombre", req.nombre);
        body.put("descripcion", req.descripcion);
        body.put("nivel", req.nivel);
        body.put("duracion_dias", req.duracion_dias);
        removeNulls(body);

        List<Map<String, Object>> rows = supabase.update("rutinas", id, body);
        return rows.isEmpty() ? null : mapper.convertValue(rows.get(0), Rutina.class);
    }

    public void eliminar(String id) {
        supabase.delete("rutinas", id);
    }

    private void removeNulls(Map<String, Object> body) {
        body.values().removeIf(v -> v == null);
    }
}
