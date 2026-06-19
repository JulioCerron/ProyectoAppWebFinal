package com.proyectoWeb.infraestructura.adaptadores.salida.persistencia;

import com.proyectoWeb.dominio.modelo.Material;
import com.proyectoWeb.dominio.modelo.SolicitudMaterial;
import com.proyectoWeb.dominio.puertos.salida.LogisticaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class LogisticaPersistenciaAdapter implements LogisticaRepositoryPort {
	private final MaterialRepository materialRepository;
	private final SolicitudMaterialRepository solicitudRepository;

	@Override
	public Mono<Material> guardarMaterial(Material material) {
		return materialRepository.save(mapearAMatEntity(material)).map(this::mapearAMatDominio);
	}

	@Override
	public Flux<Material> buscarMaterialesTodos() {
		return materialRepository.findAll().map(this::mapearAMatDominio);
	}

	@Override
	public Mono<Material> buscarMaterialPorId(Long id) {
		return materialRepository.findById(id).map(this::mapearAMatDominio);
	}

	@Override
	public Mono<SolicitudMaterial> guardarSolicitud(SolicitudMaterial solicitud) {
		return solicitudRepository.save(mapearASolEntity(solicitud)).flatMap(this::enriquecerSolicitud);
	}

	@Override
	public Flux<SolicitudMaterial> buscarSolicitudesPorIncidencia(Long incidenciaId) {
		return solicitudRepository.findByIncidenciaId(incidenciaId).flatMap(this::enriquecerSolicitud);
	}

	@Override
	public Flux<SolicitudMaterial> buscarSolicitudesTodas() {
		return solicitudRepository.findAll().flatMap(this::enriquecerSolicitud);
	}

	private Mono<SolicitudMaterial> enriquecerSolicitud(SolicitudMaterialEntity entity) {
		return materialRepository.findById(entity.getMaterialId()).map(mat -> {
			SolicitudMaterial dom = mapearASolDominio(entity);
			dom.setNombreMaterial(mat.getNombre());
			return dom;
		}).defaultIfEmpty(mapearASolDominio(entity));
	}

	private Material mapearAMatDominio(MaterialEntity e) {
		return Material.builder().id(e.getId()).nombre(e.getNombre()).descripcion(e.getDescripcion())
				.stockActual(e.getStockActual()).stockMinimo(e.getStockMinimo()).unidadMedida(e.getUnidadMedida())
				.build();
	}

	private MaterialEntity mapearAMatEntity(Material d) {
		return MaterialEntity.builder().id(d.getId()).nombre(d.getNombre()).descripcion(d.getDescripcion())
				.stockActual(d.getStockActual()).stockMinimo(d.getStockMinimo()).unidadMedida(d.getUnidadMedida())
				.build();
	}

	private SolicitudMaterial mapearASolDominio(SolicitudMaterialEntity e) {
		return SolicitudMaterial.builder().id(e.getId()).incidenciaId(e.getIncidenciaId()).materialId(e.getMaterialId())
				.cantidad(e.getCantidad()).estado(e.getEstado()).fechaSolicitud(e.getFechaSolicitud()).build();
	}

	private SolicitudMaterialEntity mapearASolEntity(SolicitudMaterial d) {
		return SolicitudMaterialEntity.builder().id(d.getId()).incidenciaId(d.getIncidenciaId())
				.materialId(d.getMaterialId()).cantidad(d.getCantidad()).estado(d.getEstado())
				.fechaSolicitud(d.getFechaSolicitud()).build();
	}
}
