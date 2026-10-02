# Gym Microservice - Java (Spring Boot)

Microservicio REST de **ejercicios, rutinas y comidas** conectado a la **misma base de datos Supabase** que el microservicio original. Expone el mismo contrato de API (mismos métodos, rutas y campos) para ser intercambiable con los microservicios en Python, Node y Go.

## Stack

- Java 21 + Spring Boot 3.3
- Cliente HTTP hacia Supabase REST (PostgREST)
- springdoc-openapi (Swagger UI)
- CORS habilitado globalmente

## Requisitos

- JDK 21
- Maven 3.9+

## Configuración

Copia `.env.example` a `.env` y coloca las credenciales (mismas que el proyecto original):

```env
SUPABASE_URL=https://tu-proyecto.supabase.co
SUPABASE_KEY=tu_key_de_supabase
PORT=8080
```

## Levantar

```bash
mvn spring-boot:run
```

O compilando el jar:

```bash
mvn -DskipTests package
java -jar target/gym-java-1.0.0.jar
```

## Probar desde Swagger

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## Endpoints

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/` | Info de la API |
| GET | `/ejercicios` | Listar ejercicios |
| GET | `/ejercicios/{id}` | Obtener un ejercicio |
| POST | `/ejercicios` | Crear ejercicio |
| PUT | `/ejercicios/{id}` | Actualizar ejercicio |
| DELETE | `/ejercicios/{id}` | Eliminar ejercicio |
| GET | `/rutinas` | Listar rutinas |
| GET | `/rutinas/{id}` | Obtener rutina con sus ejercicios |
| POST | `/rutinas` | Crear rutina |
| PUT | `/rutinas/{id}` | Actualizar rutina |
| DELETE | `/rutinas/{id}` | Eliminar rutina |
| GET | `/comidas` | Listar comidas |
| GET | `/comidas/{id}` | Obtener comida |
| POST | `/comidas` | Crear comida |
| PUT | `/comidas/{id}` | Actualizar comida |
| DELETE | `/comidas/{id}` | Eliminar comida |
