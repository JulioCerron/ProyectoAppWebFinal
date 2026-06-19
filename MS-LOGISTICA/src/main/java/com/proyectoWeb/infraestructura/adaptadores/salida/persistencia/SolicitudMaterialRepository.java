package com.proyectoWeb.infraestructura.adaptadores.salida.persistencia;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface SolicitudMaterialRepository extends ReactiveCrudRepository<SolicitudMaterialEntity, Long> {
	Flux<SolicitudMaterialEntity> findByIncidenciaId(Long incidenciaId);
}
