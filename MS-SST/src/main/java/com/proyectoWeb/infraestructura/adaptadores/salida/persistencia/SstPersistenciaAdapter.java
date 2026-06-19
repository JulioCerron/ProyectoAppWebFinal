package com.proyectoWeb.infraestructura.adaptadores.salida.persistencia;

import com.proyectoWeb.dominio.modelo.EvaluacionRiesgo;
import com.proyectoWeb.dominio.modelo.Observacion;
import com.proyectoWeb.dominio.puertos.salida.SstRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SstPersistenciaAdapter implements SstRepositoryPort {
	private final ObservacionRepository obsRepo;
	private final EvaluacionRepository evaRepo;

	@Override
	public Mono<Observacion> guardarObservacion(Observacion d) {
		return obsRepo
				.save(ObservacionEntity.builder().incidenciaId(d.getIncidenciaId()).descripcion(d.getDescripcion())
						.fechaRegistro(d.getFechaRegistro()).observadoPor(d.getObservadoPor()).build())
				.map(this::mapObsToDomain);
	}

	@Override
	public Flux<Observacion> buscarObservacionesPorIncidencia(Long incidenciaId) {
		return obsRepo.findByIncidenciaId(incidenciaId).map(this::mapObsToDomain);
	}

	@Override
	public Mono<Observacion> buscarObservacionPorId(Long id) {
		return obsRepo.findById(id).map(this::mapObsToDomain);
	}

	@Override
	public Mono<EvaluacionRiesgo> guardarEvaluacion(EvaluacionRiesgo d) {
		return evaRepo.save(EvaluacionRiesgoEntity.builder().id(d.getId()).observacionId(d.getObservacionId())
				.nivelRiesgo(d.getNivelRiesgo()).medidasPreventivas(d.getMedidasPreventivas()).aprobado(d.getAprobado())
				.build()).map(this::mapEvaToDomain);
	}

	@Override
	public Mono<EvaluacionRiesgo> buscarEvaluacionPorObservacion(Long observacionId) {
		return evaRepo.findByObservacionId(observacionId).map(this::mapEvaToDomain);
	}

	private Observacion mapObsToDomain(ObservacionEntity e) {
		return Observacion.builder().id(e.getId()).incidenciaId(e.getIncidenciaId()).descripcion(e.getDescripcion())
				.fechaRegistro(e.getFechaRegistro()).observadoPor(e.getObservadoPor()).build();
	}

	private EvaluacionRiesgo mapEvaToDomain(EvaluacionRiesgoEntity e) {
		return EvaluacionRiesgo.builder().id(e.getId()).observacionId(e.getObservacionId())
				.nivelRiesgo(e.getNivelRiesgo()).medidasPreventivas(e.getMedidasPreventivas()).aprobado(e.getAprobado())
				.build();
	}
}
