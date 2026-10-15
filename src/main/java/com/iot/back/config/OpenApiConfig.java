package com.iot.back.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de OpenAPI/Swagger para documentar el API REST del backend IoT.
 *
 * Acceso:
 * - Swagger UI: http://localhost:8080/swagger-ui.html
 * - OpenAPI JSON: http://localhost:8080/v3/api-docs
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("IoT Telemetry Backend API")
                        .version("1.0.0")
                        .description("API REST para recibir y consultar promedios de telemetría desde el Edge (FastAPI). "
                                + "El Edge calcula promedios de temperatura y humedad con datos del sensor DHT22 en el ESP32.")
                        .contact(new Contact()
                                .name("IoT Team")
                                .url("https://github.com/tu-usuario/iot-telemetry"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
