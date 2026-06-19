package com.proyectoWeb.infraestructura.adaptadores.entrada.web;

import com.proyectoWeb.aplicacion.dto.EvaluacionRiesgoDTO;
import com.proyectoWeb.aplicacion.dto.ObservacionDTO;
import com.proyectoWeb.dominio.modelo.EvaluacionRiesgo;
import com.proyectoWeb.dominio.modelo.Observacion;
import com.proyectoWeb.dominio.puertos.entrada.SstPortIn;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sst")
@RequiredArgsConstructor
@Tag(name = "Seguridad y Salud en el Trabajo")
public class SstController {
	private final SstPortIn sstPortIn;

	@PostMapping("/observaciones")
	@Operation(summary = "Registrar una nueva observación de seguridad")
	public Mono<ObservacionDTO> crearObservacion(@Valid @RequestBody ObservacionDTO dto) {
		Observacion obs = Observacion.builder().incidenciaId(dto.getIncidenciaId()).descripcion(dto.getDescripcion())
				.observadoPor(dto.getObservadoPor()).nivelRiesgo(dto.getNivelRiesgo()).build();

		return sstPortIn.registrarObservacion(obs).map(this::mapObsToDto);
	}

	@GetMapping("/incidencia/{id}/observaciones")
	@Operation(summary = "Listar observaciones de una incidencia específica")
	public Mono<List<ObservacionDTO>> listarPorIncidencia(@PathVariable Long id) {
		return sstPortIn.listarPorIncidencia(id).map(this::mapObsToDto).collectList();
	}

	@PutMapping("/observaciones/{id}/riesgo")
	@Operation(summary = "Actualizar el nivel de riesgo para desbloquear el flujo")
	public Mono<EvaluacionRiesgoDTO> actualizarRiesgo(@PathVariable Long id, @RequestParam String nuevoNivel) {
		return sstPortIn.actualizarRiesgo(id, nuevoNivel).map(this::mapEvaToDto);
	}

	@PatchMapping("/evaluaciones/{observacionId}")
	@Operation(summary = "Completar campos de aprobación y medidas preventivas de una evaluación existente")
	public Mono<EvaluacionRiesgoDTO> completarEvaluacion(@PathVariable Long observacionId,
			@RequestBody EvaluacionRiesgoDTO dto) {

		EvaluacionRiesgo datos = EvaluacionRiesgo.builder().aprobado(dto.getAprobado())
				.medidasPreventivas(dto.getMedidasPreventivas()).nivelRiesgo(dto.getNivelRiesgo()).build();

		return sstPortIn.completarEvaluacion(observacionId, datos).map(this::mapEvaToDto);
	}

	private ObservacionDTO mapObsToDto(Observacion d) {
		ObservacionDTO dto = new ObservacionDTO();
		dto.setId(d.getId());
		dto.setIncidenciaId(d.getIncidenciaId());
		dto.setDescripcion(d.getDescripcion());
		dto.setFechaRegistro(d.getFechaRegistro());
		dto.setObservadoPor(d.getObservadoPor());
		dto.setNivelRiesgo(d.getNivelRiesgo());
		return dto;
	}

	private EvaluacionRiesgoDTO mapEvaToDto(EvaluacionRiesgo d) {
		EvaluacionRiesgoDTO dto = new EvaluacionRiesgoDTO();
		dto.setId(d.getId());
		dto.setObservacionId(d.getObservacionId());
		dto.setNivelRiesgo(d.getNivelRiesgo());
		dto.setMedidasPreventivas(d.getMedidasPreventivas());
		dto.setAprobado(d.getAprobado());
		return dto;
	}
}
