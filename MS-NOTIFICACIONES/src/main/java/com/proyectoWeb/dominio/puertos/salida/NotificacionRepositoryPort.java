package com.proyectoWeb.dominio.puertos.salida;

import com.proyectoWeb.dominio.modelo.Notificacion;
import reactor.core.publisher.Mono;

public interface NotificacionRepositoryPort {
	Mono<Notificacion> guardar(Notificacion notificacion);
}
