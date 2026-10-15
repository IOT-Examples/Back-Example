package com.iot.back.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

/**
 * Entidad que representa un registro de telemetría promedio recibido desde el Edge.
 * Se persiste en PostgreSQL y es consultable a través del endpoint GET.
 */
@Entity
@Table(name = "telemetry_averages")
public class TelemetryAverage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_id", nullable = false, length = 128)
    private String deviceId;

    @Column(name = "samples", nullable = false)
    private Integer samples;

    @Column(name = "average_temperature", nullable = false)
    private Double averageTemperature;

    @Column(name = "average_humidity", nullable = false)
    private Double averageHumidity;

    @Column(name = "received_at", nullable = false, updatable = false)
    private Instant receivedAt;

    // Constructor por defecto requerido por JPA
    public TelemetryAverage() {
    }

    /**
     * Constructor completo usado por el servicio al crear un nuevo registro.
     *
     * @param deviceId identificador del dispositivo
     * @param samples número de lecturas usadas para calcular el promedio
     * @param averageTemperature temperatura promedio en °C
     * @param averageHumidity humedad promedio en %
     * @param receivedAt instante de recepción generado por el backend
     */
    public TelemetryAverage(String deviceId, Integer samples,
                            Double averageTemperature, Double averageHumidity,
                            Instant receivedAt) {
        this.deviceId = deviceId;
        this.samples = samples;
        this.averageTemperature = averageTemperature;
        this.averageHumidity = averageHumidity;
        this.receivedAt = receivedAt;
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

    @Override
    public String toString() {
        return "TelemetryAverage{" +
                "id=" + id +
                ", deviceId='" + deviceId + '\'' +
                ", samples=" + samples +
                ", averageTemperature=" + averageTemperature +
                ", averageHumidity=" + averageHumidity +
                ", receivedAt=" + receivedAt +
                '}';
    }
}
