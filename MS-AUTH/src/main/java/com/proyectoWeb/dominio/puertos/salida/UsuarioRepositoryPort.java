package com.proyectoWeb.dominio.puertos.salida;

import com.proyectoWeb.dominio.model.Usuario;
import reactor.core.publisher.Mono;

public interface UsuarioRepositoryPort {
	Mono<Usuario> findByNombreUsuario(String username);

	Mono<Usuario> guardar(Usuario user);
}