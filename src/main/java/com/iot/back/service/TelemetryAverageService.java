package com.iot.back.service;

import com.iot.back.dto.TelemetryAverageRequestDto;
import com.iot.back.dto.TelemetryAverageResponseDto;
import com.iot.back.entity.TelemetryAverage;
import com.iot.back.repository.TelemetryAverageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * Servicio que gestiona la lógica de negocio de telemetría.
 * Se encarga de crear y consultar registros de promedios.
 */
@Service
public class TelemetryAverageService {

    private static final Logger log = LoggerFactory.getLogger(TelemetryAverageService.class);

    private final TelemetryAverageRepository repository;

    public TelemetryAverageService(TelemetryAverageRepository repository) {
        this.repository = repository;
    }

    /**
     * Crea y persiste un nuevo registro de telemetría promedio.
     * Genera el instante de recepción automáticamente.
     *
     * @param dto DTO con los datos validados
     * @return DTO de respuesta con el registro persistido
     */
    @Transactional
    public TelemetryAverageResponseDto saveAverage(TelemetryAverageRequestDto dto) {
        log.info("Registrando telemetría para dispositivo: {}", dto.getDeviceId());

        TelemetryAverage entity = new TelemetryAverage(
                dto.getDeviceId(),
                dto.getSamples(),
                dto.getAverageTemperature(),
                dto.getAverageHumidity(),
                Instant.now()
        );

        TelemetryAverage saved = repository.save(entity);
        log.info("Registro guardado con ID: {}", saved.getId());

        return new TelemetryAverageResponseDto(saved);
    }

    /**
     * Obtiene todos los registros paginados, del más reciente al más antiguo.
     *
     * @param pageable configuración de paginación
     * @return página de DTOs de respuesta
     */
    @Transactional(readOnly = true)
    public Page<TelemetryAverageResponseDto> getAllAverages(Pageable pageable) {
        log.debug("Consultando registros de telemetría con paginación: {}", pageable);
        return repository.findAllByOrderByReceivedAtDesc(pageable)
                .map(TelemetryAverageResponseDto::new);
    }
}
