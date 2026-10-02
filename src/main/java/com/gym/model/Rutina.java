package com.gym.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public class Rutina {

    public UUID id;
    public String nombre;
    public String descripcion;
    public String nivel;
    public Integer duracion_dias;
    public OffsetDateTime created_at;
}
