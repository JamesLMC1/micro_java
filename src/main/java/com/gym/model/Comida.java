package com.gym.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class Comida {

    public UUID id;
    public String nombre;
    public String tipo;
    public Integer calorias;
    public BigDecimal proteinas_g;
    public BigDecimal carbos_g;
    public BigDecimal grasas_g;
    public String receta;
    public OffsetDateTime created_at;
}
