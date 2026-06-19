package com.proyectoWeb.aplicacion.usecase;

import com.proyectoWeb.dominio.modelo.Notificacion;
import com.proyectoWeb.dominio.puertos.entrada.ProcesarNotificacionPortIn;
import com.proyectoWeb.dominio.puertos.salida.EmailServicePort;
import com.proyectoWeb.dominio.puertos.salida.NotificacionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificacionUseCase implements ProcesarNotificacionPortIn {
	private final NotificacionRepositoryPort repositoryPort;
	private final EmailServicePort emailServicePort;

	@Override
	public Mono<Void> procesarEventoIncidencia(Notificacion evento) {
		evento.setFechaEnvio(LocalDateTime.now());
		evento.setTipo("CORREO");

		return repositoryPort.guardar(evento).flatMap(n -> emailServicePort.enviarCorreo(n.getDestinatario(),
				"Alerta de Incidencia - HANDFAST", n.getMensaje()));
	}
}
