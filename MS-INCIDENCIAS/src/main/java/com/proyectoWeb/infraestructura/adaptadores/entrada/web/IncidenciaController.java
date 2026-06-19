package com.proyectoWeb.infraestructura.adaptadores.entrada.web;

import com.proyectoWeb.aplicacion.dto.IncidenciaDTO;
import com.proyectoWeb.aplicacion.dto.MaterialDTO;
import com.proyectoWeb.aplicacion.usecase.IncidenciaUseCase;
import com.proyectoWeb.dominio.modelo.Incidencia;
import com.proyectoWeb.dominio.puertos.entrada.IncidenciaPortIn;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/incidents")
@RequiredArgsConstructor
@Tag(name = "Núcleo de Incidencias", description = "Gestión del ciclo de vida de incidencias operativas")
public class IncidenciaController {
	private final IncidenciaPortIn incidenciaPortIn;

	@PostMapping
	@Operation(summary = "Registrar una nueva incidencia")
	public ResponseEntity<IncidenciaDTO> registrar(@Valid @RequestBody IncidenciaDTO dto) {
		Incidencia nueva = incidenciaPortIn.registrar(mapearADominio(dto));
		return ResponseEntity.ok(mapearADto(nueva));
	}

	@GetMapping
	@Operation(summary = "Listar todas las incidencias")
	public List<IncidenciaDTO> listar() {
		return incidenciaPortIn.listarTodas().stream().map(this::mapearADto).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtener incidencia por ID")
	public ResponseEntity<IncidenciaDTO> obtenerPorId(@PathVariable Long id) {
		return ResponseEntity.ok(mapearADto(incidenciaPortIn.obtenerPorId(id)));
	}

	@PutMapping("/{id}/asignar/{empleadoId}")
	@Operation(summary = "Asignar un técnico responsable a la incidencia")
	public ResponseEntity<IncidenciaDTO> asignar(@PathVariable Long id, @PathVariable Long empleadoId) {
		return ResponseEntity.ok(mapearADto(incidenciaPortIn.asignarResponsable(id, empleadoId)));
	}

	@PatchMapping("/{id}/estado")
	@Operation(summary = "Cambiar el estado de una incidencia (Ej: EN_PROCESO, ATENDIDA, CERRADA)")
	public ResponseEntity<IncidenciaDTO> cambiarEstado(@PathVariable Long id, @RequestParam String nuevoEstado) {
		return ResponseEntity.ok(mapearADto(incidenciaPortIn.cambiarEstado(id, nuevoEstado)));
	}

	@GetMapping("/materiales-disponibles")
	@Operation(summary = "Listar materiales disponibles en stock (vía Logística)")
	public List<MaterialDTO> getMateriales() {
		return ((IncidenciaUseCase) incidenciaPortIn).obtenerMaterialesDisponibles();
	}

	@PostMapping("/{id}/solicitar-material")
	@Operation(summary = "El técnico solicita material para la incidencia")
	public ResponseEntity<String> pedirMaterial(@PathVariable Long id, @RequestParam Long materialId,
			@RequestParam Integer cantidad) {

		((IncidenciaUseCase) incidenciaPortIn).solicitarMateriales(id, materialId, cantidad);
		return ResponseEntity.ok("Solicitud enviada a Logística correctamente.");
	}

	private Incidencia mapearADominio(IncidenciaDTO dto) {
		return Incidencia.builder().id(dto.getId()).titulo(dto.getTitulo()).descripcion(dto.getDescripcion())
				.prioridad(dto.getPrioridad()).estado(dto.getEstado()).areaId(dto.getAreaId())
				.reportadoPorId(dto.getReportadoPorId()).asignadoAId(dto.getAsignadoAId()).build();
	}

	private IncidenciaDTO mapearADto(Incidencia d) {
		return IncidenciaDTO.builder().id(d.getId()).titulo(d.getTitulo()).descripcion(d.getDescripcion())
				.prioridad(d.getPrioridad()).estado(d.getEstado()).areaId(d.getAreaId())
				.reportadoPorId(d.getReportadoPorId()).asignadoAId(d.getAsignadoAId())
				.fechaCreacion(d.getFechaCreacion()).fechaCierre(d.getFechaCierre()).build();
	}
}
