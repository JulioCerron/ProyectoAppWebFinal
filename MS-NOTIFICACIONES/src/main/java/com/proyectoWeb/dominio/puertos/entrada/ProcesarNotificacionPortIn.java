package com.proyectoWeb.dominio.puertos.entrada;

import com.proyectoWeb.dominio.modelo.Notificacion;
import reactor.core.publisher.Mono;

public interface ProcesarNotificacionPortIn {
	Mono<Void> procesarEventoIncidencia(Notificacion evento);
}
