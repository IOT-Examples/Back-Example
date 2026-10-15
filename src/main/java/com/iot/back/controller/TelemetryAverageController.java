package com.iot.back.controller;

import com.iot.back.dto.TelemetryAverageRequestDto;
import com.iot.back.dto.TelemetryAverageResponseDto;
import com.iot.back.service.TelemetryAverageService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para la gestión de telemetría.
 * Expone los endpoints POST y GET bajo /api/v1/telemetry/averages.
 */
@RestController
@RequestMapping("/api/v1/telemetry/averages")
public class TelemetryAverageController {

    private final TelemetryAverageService service;

    public TelemetryAverageController(TelemetryAverageService service) {
        this.service = service;
    }

    /**
     * Recibe y persiste un promedio calculado por el Edge.
     *
     * @param dto DTO validado con los datos del promedio
     * @return HTTP 201 con el registro guardado
     */
    @PostMapping
    public ResponseEntity<TelemetryAverageResponseDto> createAverage(
            @Valid @RequestBody TelemetryAverageRequestDto dto) {

        TelemetryAverageResponseDto response = service.saveAverage(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Lista todos los registros de telemetría del más reciente al más antiguo.
     *
     * @param page número de página (desde 0)
     * @param size tamaño de página
     * @return página de registros
     */
    @GetMapping
    public ResponseEntity<Page<TelemetryAverageResponseDto>> listAverages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        // Limitar tamaño máximo de página para evitar sobrecarga
        int safeSize = Math.min(size, 100);
        Pageable pageable = PageRequest.of(page, safeSize);

        Page<TelemetryAverageResponseDto> result = service.getAllAverages(pageable);
        return ResponseEntity.ok(result);
    }
}
