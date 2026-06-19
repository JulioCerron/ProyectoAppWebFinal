package com.proyectoWeb.infraestructura.adaptadores.entrada.eventos;

import com.proyectoWeb.dominio.modelo.Notificacion;
import com.proyectoWeb.dominio.puertos.entrada.ProcesarNotificacionPortIn;
import com.proyectoWeb.infraestructura.configuracion.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQListener {
	private final ProcesarNotificacionPortIn procesarNotificacionPortIn;

	@RabbitListener(queues = RabbitMQConfig.COLA_NOTIFICACIONES)
	public void recibirEvento(Notificacion evento) {
		procesarNotificacionPortIn.procesarEventoIncidencia(evento).subscribe();
	}
}
