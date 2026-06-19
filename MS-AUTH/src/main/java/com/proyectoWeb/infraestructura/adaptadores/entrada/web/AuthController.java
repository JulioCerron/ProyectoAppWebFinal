package com.proyectoWeb.infraestructura.adaptadores.entrada.web;

import com.proyectoWeb.aplicacion.dto.AuthResponse;
import com.proyectoWeb.aplicacion.dto.LoginRequest;
import com.proyectoWeb.dominio.model.Usuario;
import com.proyectoWeb.dominio.puertos.entrada.AutenticacionPortIn;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints para Login y Registro")
public class AuthController {

	private final AutenticacionPortIn autenticacionPortIn;

	@PostMapping("/login")
	@Operation(summary = "Iniciar sesión para obtener un token JWT")
	public Mono<AuthResponse> login(@RequestBody LoginRequest request) {
		return autenticacionPortIn.login(request);
	}

	@PostMapping("/registro")
	@Operation(summary = "Registrar un nuevo usuario en el sistema")
	public Mono<Usuario> registro(@RequestBody Usuario usuario) {
		return autenticacionPortIn.registrar(usuario);
	}
}