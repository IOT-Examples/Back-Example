package com.iot.back.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iot.back.entity.TelemetryAverage;

import java.time.Instant;

/**
 * DTO de salida que representa un registro de telemetría persistido.
 * Se construye a partir de la entidad y se devuelve al cliente con HTTP 201.
 */
public class TelemetryAverageResponseDto {

    private Long id;

    @JsonProperty("device_id")
    private String deviceId;

    private Integer samples;

    @JsonProperty("average_temperature")
    private Double averageTemperature;

    @JsonProperty("average_humidity")
    private Double averageHumidity;

    @JsonProperty("received_at")
    private Instant receivedAt;

    /**
     * Constructor que mapea desde la entidad JPA.
     *
     * @param entity entidad persistida
     */
    public TelemetryAverageResponseDto(TelemetryAverage entity) {
        this.id = entity.getId();
        this.deviceId = entity.getDeviceId();
        this.samples = entity.getSamples();
        this.averageTemperature = entity.getAverageTemperature();
        this.averageHumidity = entity.getAverageHumidity();
        this.receivedAt = entity.getReceivedAt();
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Instant getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Instant receivedAt) {
        this.receivedAt = receivedAt;
    }
}
