package com.iot.back.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO de entrada para recibir los promedios calculados por el Edge.
 * Incluye todas las validaciones necesarias para garantizar la integridad de los datos.
 */
@Schema(description = "Promedios de telemetría calculados por el Edge y enviados al backend")
public class TelemetryAverageRequestDto {

    @Schema(description = "Identificador único del dispositivo IoT", example = "esp32_001", requiredMode = Schema.RequiredMode.REQUIRED)

    @NotBlank(message = "device_id es obligatorio y no puede estar vacío")
    @JsonProperty("device_id")
    private String deviceId;

    @Schema(description = "Número de lecturas utilizadas para calcular el promedio", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "samples es obligatorio")
    @Min(value = 1, message = "samples debe ser mayor o igual a 1")
    @JsonProperty("samples")
    private Integer samples;

    @NotNull(message = "average_temperature es obligatorio")
    @DecimalMin(value = "-40.0", message = "Temperatura mínima aceptada: -40 °C")
    @DecimalMax(value = "80.0", message = "Temperatura máxima aceptada: 80 °C")
    @Digits(integer = 3, fraction = 2, message = "Temperatura debe tener máximo 3 dígitos enteros y 2 decimales")
    @JsonProperty("average_temperature")
    private Double averageTemperature;

    @NotNull(message = "average_humidity es obligatorio")
    @DecimalMin(value = "0.0", message = "Humedad mínima aceptada: 0 %")
    @DecimalMax(value = "100.0", message = "Humedad máxima aceptada: 100 %")
    @Digits(integer = 3, fraction = 2, message = "Humedad debe tener máximo 3 dígitos enteros y 2 decimales")
    @JsonProperty("average_humidity")
    private Double averageHumidity;

    // Getters y Setters

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getSamples() {
        return samples;
    }

    public void setSamples(Integer samples) {
        this.samples = samples;
    }

    public Double getAverageTemperature() {
        return averageTemperature;
    }

    public void setAverageTemperature(Double averageTemperature) {
        this.averageTemperature = averageTemperature;
    }

    public Double getAverageHumidity() {
        return averageHumidity;
    }

    public void setAverageHumidity(Double averageHumidity) {
        this.averageHumidity = averageHumidity;
    }

    @Override
    public String toString() {
        return "TelemetryAverageRequestDto{" +
                "deviceId='" + deviceId + '\'' +
                ", samples=" + samples +
                ", averageTemperature=" + averageTemperature +
                ", averageHumidity=" + averageHumidity +
                '}';
    }
}
