package com.gym.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym.config.SupabaseClient;
import com.gym.dto.ComidaRequest;
import com.gym.exception.ApiException;
import com.gym.model.Comida;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ComidaService {

    private final SupabaseClient supabase;
    private final ObjectMapper mapper;

    public ComidaService(SupabaseClient supabase, ObjectMapper mapper) {
        this.supabase = supabase;
        this.mapper = mapper;
    }

    public List<Comida> listar() {
        return supabase.select("comidas", "select=*").stream()
                .map(row -> mapper.convertValue(row, Comida.class))
                .toList();
    }

    public Comida obtener(String id) {
        List<Map<String, Object>> rows = supabase.select("comidas", "select=*&id=eq." + id);
        if (rows.isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Comida no encontrada");
        }
        return mapper.convertValue(rows.get(0), Comida.class);
    }

    public Comida crear(ComidaRequest req) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("nombre", req.nombre);
        body.put("tipo", req.tipo);
        body.put("calorias", req.calorias);
        body.put("proteinas_g", req.proteinas_g);
        body.put("carbos_g", req.carbos_g);
        body.put("grasas_g", req.grasas_g);
        body.put("receta", req.receta);
        removeNulls(body);

        List<Map<String, Object>> rows = supabase.insert("comidas", body);
        return rows.isEmpty() ? null : mapper.convertValue(rows.get(0), Comida.class);
    }

    public Comida actualizar(String id, ComidaRequest req) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("nombre", req.nombre);
        body.put("tipo", req.tipo);
        body.put("calorias", req.calorias);
        body.put("proteinas_g", req.proteinas_g);
        body.put("carbos_g", req.carbos_g);
        body.put("grasas_g", req.grasas_g);
        body.put("receta", req.receta);
        removeNulls(body);

        List<Map<String, Object>> rows = supabase.update("comidas", id, body);
        return rows.isEmpty() ? null : mapper.convertValue(rows.get(0), Comida.class);
    }

    public void eliminar(String id) {
        supabase.delete("comidas", id);
    }

    private void removeNulls(Map<String, Object> body) {
        body.values().removeIf(v -> v == null);
    }
}
