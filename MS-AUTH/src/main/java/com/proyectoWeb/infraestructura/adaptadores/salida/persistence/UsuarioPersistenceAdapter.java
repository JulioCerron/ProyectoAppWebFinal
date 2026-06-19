package com.proyectoWeb.infraestructura.adaptadores.salida.persistence;

import com.proyectoWeb.dominio.model.Usuario;
import com.proyectoWeb.dominio.puertos.salida.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UsuarioPersistenceAdapter implements UsuarioRepositoryPort {
	private final UsuarioRepository usuarioRepository;

	@Override
	public Mono<Usuario> findByNombreUsuario(String nombreUsuario) {
		return usuarioRepository.findByNombreUsuario(nombreUsuario).map(this::mapearAEntidadDominio);
	}

	@Override
	public Mono<Usuario> guardar(Usuario usuario) {
		return usuarioRepository.save(mapearAEntidadPersistencia(usuario)).map(this::mapearAEntidadDominio);
	}

	private Usuario mapearAEntidadDominio(UsuarioEntity entidad) {
		return Usuario.builder().id(entidad.getId()).nombreUsuario(entidad.getNombreUsuario())
				.contrasena(entidad.getContrasena()).correo(entidad.getCorreo()).activo(entidad.getActivo()).build();
	}

	private UsuarioEntity mapearAEntidadPersistencia(Usuario usuario) {
		return UsuarioEntity.builder().id(usuario.getId()).nombreUsuario(usuario.getNombreUsuario())
				.contrasena(usuario.getContrasena()).correo(usuario.getCorreo()).activo(usuario.getActivo()).build();
	}
}