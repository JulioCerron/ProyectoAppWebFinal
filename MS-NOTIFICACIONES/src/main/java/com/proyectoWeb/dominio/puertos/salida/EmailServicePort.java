package com.proyectoWeb.dominio.puertos.salida;

import reactor.core.publisher.Mono;

public interface EmailServicePort {
	Mono<Void> enviarCorreo(String destinatario, String asunto, String mensaje);
}
