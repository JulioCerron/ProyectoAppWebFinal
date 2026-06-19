package com.proyectoWeb.infraestructura.adaptadores.entrada.web;

import com.proyectoWeb.aplicacion.dto.MaterialDTO;
import com.proyectoWeb.aplicacion.dto.SolicitudMaterialDTO;
import com.proyectoWeb.dominio.modelo.Material;
import com.proyectoWeb.dominio.modelo.SolicitudMaterial;
import com.proyectoWeb.dominio.puertos.entrada.GestionLogisticaPortIn;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/logistica")
@RequiredArgsConstructor
@Tag(name = "Logística e Inventario")
public class LogisticaController {
	private final GestionLogisticaPortIn logisticaPortIn;

	@PostMapping("/inventario")
	@Operation(summary = "Registrar nuevo material en almacén")
	public Mono<MaterialDTO> registrarMaterial(@Valid @RequestBody MaterialDTO dto) {
		return logisticaPortIn.registrarMaterial(mapearMatADominio(dto)).map(this::mapearMatADto);
	}

	@GetMapping("/inventario")
	@Operation(summary = "Listar todo el inventario")
	public Flux<MaterialDTO> listarInventario() {
		return logisticaPortIn.listarInventario().map(this::mapearMatADto);
	}

	@PostMapping("/solicitudes")
	@Operation(summary = "Crear solicitud de material para una incidencia")
	public Mono<SolicitudMaterialDTO> crearSolicitud(@RequestBody SolicitudMaterialDTO dto) {
		return logisticaPortIn.crearSolicitud(mapearSolADominio(dto)).map(this::mapearSolADto);
	}

	@PutMapping("/solicitudes/{id}/atender")
	@Operation(summary = "Cambiar estado de solicitud (Ej: ENTREGADO)")
	public Mono<SolicitudMaterialDTO> atenderSolicitud(@PathVariable Long id, @RequestParam String estado) {
		return logisticaPortIn.atenderSolicitud(id, estado).map(this::mapearSolADto);
	}

	private Material mapearMatADominio(MaterialDTO dto) {
		return Material.builder().id(dto.getId()).nombre(dto.getNombre()).descripcion(dto.getDescripcion())
				.stockActual(dto.getStockActual()).stockMinimo(dto.getStockMinimo()).unidadMedida(dto.getUnidadMedida())
				.build();
	}

	private MaterialDTO mapearMatADto(Material dom) {
		MaterialDTO dto = new MaterialDTO();
		dto.setId(dom.getId());
		dto.setNombre(dom.getNombre());
		dto.setDescripcion(dom.getDescripcion());
		dto.setStockActual(dom.getStockActual());
		dto.setStockMinimo(dom.getStockMinimo());
		dto.setUnidadMedida(dom.getUnidadMedida());
		return dto;
	}

	private SolicitudMaterial mapearSolADominio(SolicitudMaterialDTO dto) {
		return SolicitudMaterial.builder().incidenciaId(dto.getIncidenciaId()).materialId(dto.getMaterialId())
				.cantidad(dto.getCantidad()).build();
	}

	private SolicitudMaterialDTO mapearSolADto(SolicitudMaterial dom) {
		SolicitudMaterialDTO dto = new SolicitudMaterialDTO();
		dto.setId(dom.getId());
		dto.setIncidenciaId(dom.getIncidenciaId());
		dto.setMaterialId(dom.getMaterialId());
		dto.setNombreMaterial(dom.getNombreMaterial());
		dto.setCantidad(dom.getCantidad());
		dto.setEstado(dom.getEstado());
		dto.setFechaSolicitud(dom.getFechaSolicitud());
		return dto;
	}
}
