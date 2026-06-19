package com.proyectoWeb.dominio.puertos.entrada;

import com.proyectoWeb.dominio.modelo.EvaluacionRiesgo;
import com.proyectoWeb.dominio.modelo.Observacion;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SstPortIn {
	Mono<Observacion> registrarObservacion(Observacion observacion);

	Flux<Observacion> listarPorIncidencia(Long incidenciaId);

	Mono<EvaluacionRiesgo> obtenerEvaluacion(Long observacionId);

	Mono<EvaluacionRiesgo> actualizarRiesgo(Long observacionId, String nuevoNivel);

	Mono<EvaluacionRiesgo> completarEvaluacion(Long observacionId, EvaluacionRiesgo evaluacion);
}
