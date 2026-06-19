package com.proyectoWeb.infraestructura.adaptadores.salida.persistencia;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface EvaluacionRepository extends ReactiveCrudRepository<EvaluacionRiesgoEntity, Long> {
	Mono<EvaluacionRiesgoEntity> findByObservacionId(Long observacionId);
}
