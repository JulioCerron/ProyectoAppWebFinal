package com.proyectoWeb.dominio.puertos.salida;

import com.proyectoWeb.dominio.modelo.EvaluacionRiesgo;
import com.proyectoWeb.dominio.modelo.Observacion;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SstRepositoryPort {
	Mono<Observacion> guardarObservacion(Observacion observacion);

	Flux<Observacion> buscarObservacionesPorIncidencia(Long incidenciaId);

	Mono<Observacion> buscarObservacionPorId(Long id);

	Mono<EvaluacionRiesgo> guardarEvaluacion(EvaluacionRiesgo evaluacion);

	Mono<EvaluacionRiesgo> buscarEvaluacionPorObservacion(Long observacionId);
}
