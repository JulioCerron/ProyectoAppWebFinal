package com.proyectoWeb.infraestructura.adaptadores.salida.persistencia;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmpleadoRepository extends ReactiveCrudRepository<EmpleadoEntity, Long> {
	Mono<EmpleadoEntity> findByDni(String dni);

	Flux<EmpleadoEntity> findByDepartamentoId(Long departamentoId);

	Flux<EmpleadoEntity> findByCargo(String cargo);
}
