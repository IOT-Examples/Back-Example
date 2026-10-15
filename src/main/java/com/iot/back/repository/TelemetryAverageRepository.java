package com.iot.back.repository;

import com.iot.back.entity.TelemetryAverage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad TelemetryAverage.
 * Proporciona operaciones CRUD y consultas paginadas ordenadas por receivedAt descendente.
 */
@Repository
public interface TelemetryAverageRepository extends JpaRepository<TelemetryAverage, Long> {

    /**
     * Devuelve todos los registros ordenados del más reciente al más antiguo.
     *
     * @param pageable configuración de paginación
     * @return página de resultados
     */
    Page<TelemetryAverage> findAllByOrderByReceivedAtDesc(Pageable pageable);
}
