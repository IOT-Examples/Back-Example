package com.iot.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada del backend IoT.
 * Arranca Spring Boot con todas las configuraciones necesarias:
 * - Spring Web MVC
 * - Spring Data JPA con PostgreSQL
 * - Validación de DTOs
 * - Actuator para monitoreo
 */
@SpringBootApplication
public class BackApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackApplication.class, args);
    }
}
