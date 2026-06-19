package com.proyectoWeb.aplicacion.usecase;

import com.proyectoWeb.dominio.modelo.EvaluacionRiesgo;
import com.proyectoWeb.dominio.modelo.Observacion;
import com.proyectoWeb.dominio.puertos.entrada.SstPortIn;
import com.proyectoWeb.dominio.puertos.salida.SstRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SstUseCase implements SstPortIn {
	private final SstRepositoryPort repositoryPort;

	@Override
	public Mono<Observacion> registrarObservacion(Observacion observacion) {
		observacion.setFechaRegistro(LocalDateTime.now());

		return repositoryPort.guardarObservacion(observacion).flatMap(obsGuardada -> {

			if (observacion.getNivelRiesgo() != null) {
				EvaluacionRiesgo evaluacion = EvaluacionRiesgo.builder().observacionId(obsGuardada.getId())
						.nivelRiesgo(observacion.getNivelRiesgo()).medidasPreventivas("Evaluación inicial")
						.aprobado(false).build();

				return repositoryPort.guardarEvaluacion(evaluacion).map(eval -> {
					obsGuardada.setNivelRiesgo(eval.getNivelRiesgo());
					return obsGuardada;
				});
			}
			return Mono.just(obsGuardada);
		});
	}

	@Override
	public Flux<Observacion> listarPorIncidencia(Long incidenciaId) {
		return repositoryPort.buscarObservacionesPorIncidencia(incidenciaId)
				.flatMap(obs -> repositoryPort.buscarEvaluacionPorObservacion(obs.getId()).map(eval -> {
					obs.setNivelRiesgo(eval.getNivelRiesgo());
					return obs;
				}).defaultIfEmpty(obs));
	}

	@Override
	public Mono<EvaluacionRiesgo> obtenerEvaluacion(Long observacionId) {
		return repositoryPort.buscarEvaluacionPorObservacion(observacionId);
	}

	@Override
	public Mono<EvaluacionRiesgo> actualizarRiesgo(Long observacionId, String nuevoNivel) {
		return repositoryPort.buscarEvaluacionPorObservacion(observacionId).flatMap(evaluacion -> {
			evaluacion.setNivelRiesgo(nuevoNivel);
			evaluacion.setAprobado(nuevoNivel.equals("BAJO") || nuevoNivel.equals("MEDIO"));
			evaluacion.setMedidasPreventivas("Riesgo mitigado mediante levantamiento en campo.");
			return repositoryPort.guardarEvaluacion(evaluacion);
		});
	}

	@Override
	public Mono<EvaluacionRiesgo> completarEvaluacion(Long observacionId, EvaluacionRiesgo datosNuevos) {
		return repositoryPort.buscarEvaluacionPorObservacion(observacionId).flatMap(evaluacionExistente -> {

			evaluacionExistente.setAprobado(datosNuevos.getAprobado());
			evaluacionExistente.setMedidasPreventivas(datosNuevos.getMedidasPreventivas());

			if (datosNuevos.getNivelRiesgo() != null) {
				evaluacionExistente.setNivelRiesgo(datosNuevos.getNivelRiesgo());
			}

			return repositoryPort.guardarEvaluacion(evaluacionExistente);
		}).switchIfEmpty(
				Mono.error(new RuntimeException("No se encontró una evaluación inicial para esta observación.")));
	}
}
