package com.proyectoWeb.infraestructura.adaptadores.salida.persistencia;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ObservacionRepository extends ReactiveCrudRepository<ObservacionEntity, Long> {
	Flux<ObservacionEntity> findByIncidenciaId(Long incidenciaId);
}
