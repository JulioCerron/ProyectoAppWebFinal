package com.proyectoWeb.dominio.puertos.entrada;

import com.proyectoWeb.aplicacion.dto.AuthResponse;
import com.proyectoWeb.aplicacion.dto.LoginRequest;
import com.proyectoWeb.dominio.model.Usuario;

import reactor.core.publisher.Mono;

public interface AutenticacionPortIn {
	Mono<AuthResponse> login(LoginRequest request);

	Mono<Usuario> registrar(Usuario usuario);
}
