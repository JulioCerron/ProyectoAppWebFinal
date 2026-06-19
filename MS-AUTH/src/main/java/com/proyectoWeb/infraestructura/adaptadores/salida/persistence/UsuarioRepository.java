package com.proyectoWeb.infraestructura.adaptadores.salida.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UsuarioRepository extends ReactiveCrudRepository<UsuarioEntity, Long> {
	Mono<UsuarioEntity> findByNombreUsuario(String nombreUsuario);
}