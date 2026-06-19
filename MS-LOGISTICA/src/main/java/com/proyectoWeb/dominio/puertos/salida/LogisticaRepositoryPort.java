package com.proyectoWeb.dominio.puertos.salida;

import com.proyectoWeb.dominio.modelo.Material;
import com.proyectoWeb.dominio.modelo.SolicitudMaterial;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LogisticaRepositoryPort {
	
	Mono<Material> guardarMaterial(Material material);

	Flux<Material> buscarMaterialesTodos();

	Mono<Material> buscarMaterialPorId(Long id);

	Mono<SolicitudMaterial> guardarSolicitud(SolicitudMaterial solicitud);

	Flux<SolicitudMaterial> buscarSolicitudesPorIncidencia(Long incidenciaId);

	Flux<SolicitudMaterial> buscarSolicitudesTodas();
}
