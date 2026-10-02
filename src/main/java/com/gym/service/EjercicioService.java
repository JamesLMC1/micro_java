package com.gym.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym.config.SupabaseClient;
import com.gym.dto.EjercicioRequest;
import com.gym.exception.ApiException;
import com.gym.model.Ejercicio;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class EjercicioService {

    private final SupabaseClient supabase;
    private final ObjectMapper mapper;

    public EjercicioService(SupabaseClient supabase, ObjectMapper mapper) {
        this.supabase = supabase;
        this.mapper = mapper;
    }

    public List<Ejercicio> listar() {
        return supabase.select("ejercicios", "select=*").stream()
                .map(row -> mapper.convertValue(row, Ejercicio.class))
                .toList();
    }

    public Ejercicio obtener(String id) {
        List<Map<String, Object>> rows = supabase.select("ejercicios", "select=*&id=eq." + id);
        if (rows.isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Ejercicio no encontrado");
        }
        return mapper.convertValue(rows.get(0), Ejercicio.class);
    }

    public Ejercicio crear(EjercicioRequest req) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("nombre", req.nombre);
        body.put("grupo_muscular", req.grupo_muscular);
        body.put("descripcion", req.descripcion);
        body.put("dificultad", req.dificultad);
        body.put("imagen_url", req.imagen_url);
        removeNulls(body);

        List<Map<String, Object>> rows = supabase.insert("ejercicios", body);
        return rows.isEmpty() ? null : mapper.convertValue(rows.get(0), Ejercicio.class);
    }

    public Ejercicio actualizar(String id, EjercicioRequest req) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("nombre", req.nombre);
        body.put("grupo_muscular", req.grupo_muscular);
        body.put("descripcion", req.descripcion);
        body.put("dificultad", req.dificultad);
        body.put("imagen_url", req.imagen_url);
        removeNulls(body);

        List<Map<String, Object>> rows = supabase.update("ejercicios", id, body);
        return rows.isEmpty() ? null : mapper.convertValue(rows.get(0), Ejercicio.class);
    }

    public void eliminar(String id) {
        supabase.delete("ejercicios", id);
    }

    private void removeNulls(Map<String, Object> body) {
        body.values().removeIf(v -> v == null);
    }
}
