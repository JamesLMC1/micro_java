package com.gym.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym.exception.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Cliente minimo para la API REST de Supabase (PostgREST).
 * Replica el comportamiento de @supabase/supabase-js usado por el microservicio Node original.
 */
@Component
public class SupabaseClient {

    private final RestClient client;
    private final String restUrl;
    private final ObjectMapper mapper;

    public SupabaseClient(RestClient.Builder builder,
                          ObjectMapper mapper,
                          @Value("${SUPABASE_URL}") String supabaseUrl,
                          @Value("${SUPABASE_KEY}") String supabaseKey) {
        this.mapper = mapper;
        this.restUrl = supabaseUrl.replaceAll("/+$", "") + "/rest/v1";
        this.client = builder
                .defaultHeader("apikey", supabaseKey)
                .defaultHeader("Authorization", "Bearer " + supabaseKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public List<Map<String, Object>> select(String table, String query) {
        String url = restUrl + "/" + table + (isBlank(query) ? "" : "?" + query);
        try {
            List<Map<String, Object>> data = client.get()
                    .uri(URI.create(url))
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<Map<String, Object>>>() {});
            return data == null ? Collections.emptyList() : data;
        } catch (RestClientResponseException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, extractMessage(e));
        }
    }

    public List<Map<String, Object>> insert(String table, Map<String, Object> body) {
        try {
            List<Map<String, Object>> data = client.post()
                    .uri(URI.create(restUrl + "/" + table))
                    .header("Prefer", "return=representation")
                    .body(body)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<Map<String, Object>>>() {});
            return data == null ? Collections.emptyList() : data;
        } catch (RestClientResponseException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, extractMessage(e));
        }
    }

    public List<Map<String, Object>> update(String table, String id, Map<String, Object> body) {
        try {
            List<Map<String, Object>> data = client.patch()
                    .uri(URI.create(restUrl + "/" + table + "?id=eq." + id))
                    .header("Prefer", "return=representation")
                    .body(body)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<Map<String, Object>>>() {});
            return data == null ? Collections.emptyList() : data;
        } catch (RestClientResponseException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, extractMessage(e));
        }
    }

    public void delete(String table, String id) {
        try {
            client.delete()
                    .uri(URI.create(restUrl + "/" + table + "?id=eq." + id))
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientResponseException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, extractMessage(e));
        }
    }

    private String extractMessage(RestClientResponseException e) {
        String body = e.getResponseBodyAsString();
        if (!isBlank(body)) {
            try {
                JsonNode node = mapper.readTree(body);
                if (node.hasNonNull("message")) {
                    return node.get("message").asText();
                }
            } catch (Exception ignored) {
                // el cuerpo no era JSON valido: se devuelve tal cual
            }
            return body;
        }
        return e.getMessage();
    }

    private boolean isBlank(String s) {
        return s == null || s.isBlank();
    }
}
