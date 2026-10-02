package com.gym.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public class Ejercicio {

    public UUID id;
    public String nombre;
    public String grupo_muscular;
    public String descripcion;
    public String dificultad;
    public String imagen_url;
    public OffsetDateTime created_at;
}
