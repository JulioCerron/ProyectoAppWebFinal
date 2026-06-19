package com.proyectoWeb.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class FiltroAutenticacion extends AbstractGatewayFilterFactory<FiltroAutenticacion.Configuracion> {
	private final ValidadorJwt validadorJwt;

	public FiltroAutenticacion(ValidadorJwt validadorJwt) {
		super(Configuracion.class);
		this.validadorJwt = validadorJwt;
	}

	public static class Configuracion {
	}

	@Override
	public GatewayFilter apply(Configuracion config) {
		return (exchange, chain) -> {

			if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				return errorNoAutorizado(exchange, "Falta el header de autorizacion");
			}

			String headerAuth = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
				String token = headerAuth.substring(7);
				try {
					validadorJwt.validarToken(token);
				} catch (Exception e) {
					return errorNoAutorizado(exchange, "Token invalido o expirado");
				}
			}
			return chain.filter(exchange);
		};
	}

	private Mono<Void> errorNoAutorizado(ServerWebExchange exchange, String mensaje) {
		exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
		return exchange.getResponse().setComplete();
	}
}
