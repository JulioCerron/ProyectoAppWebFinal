package com.proyectoWeb.aplicacion.useCase;

import com.proyectoWeb.aplicacion.dto.AuthResponse;
import com.proyectoWeb.aplicacion.dto.LoginRequest;
import com.proyectoWeb.dominio.model.Usuario;
import com.proyectoWeb.dominio.puertos.entrada.AutenticacionPortIn;
import com.proyectoWeb.dominio.puertos.salida.UsuarioRepositoryPort;
import com.proyectoWeb.infraestructura.config.ProveedorJwt;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AutenticacionUseCase implements AutenticacionPortIn {

	private final UsuarioRepositoryPort usuarioRepositoryPort;
	private final PasswordEncoder encriptador;
	private final ProveedorJwt proveedorJwt;

	@Override
	public Mono<AuthResponse> login(LoginRequest request) {
		return usuarioRepositoryPort.findByNombreUsuario(request.getNombreUsuario())
				.filter(usuario -> encriptador.matches(request.getContrasena(), usuario.getContrasena()))
				.map(usuario -> {
					String token = proveedorJwt.generarToken(usuario);
					return new AuthResponse(token, usuario.getNombreUsuario());
				}).switchIfEmpty(Mono.error(new RuntimeException("Credenciales inválidas")));
	}

	@Override
	public Mono<Usuario> registrar(Usuario usuario) {
		usuario.setContrasena(encriptador.encode(usuario.getContrasena()));
		usuario.setActivo(true);

		if (usuario.getActivo() == null) {
			usuario.setActivo(true);
		}

		if (usuario.getRoles() == null || usuario.getRoles().isEmpty()) {
			usuario.setRoles(List.of("ROLE_SUPERVISOR"));
		}

		return usuarioRepositoryPort.guardar(usuario);
	}
}