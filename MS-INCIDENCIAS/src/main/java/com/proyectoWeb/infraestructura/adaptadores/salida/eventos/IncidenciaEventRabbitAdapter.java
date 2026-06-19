package com.proyectoWeb.infraestructura.adaptadores.salida.eventos;

import com.proyectoWeb.dominio.modelo.Incidencia;
import com.proyectoWeb.dominio.puertos.salida.IncidenciaEventPortOut;
import com.proyectoWeb.infraestructura.configuracion.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class IncidenciaEventRabbitAdapter implements IncidenciaEventPortOut {
	private final RabbitTemplate rabbitTemplate;

	@Override
	public void publicarCreacion(Incidencia incidencia) {
		Map<String, Object> evento = new HashMap<>();
		evento.put("incidenciaId", incidencia.getId());
		evento.put("mensaje", "Nueva incidencia registrada: " + incidencia.getTitulo());
		evento.put("destinatario", "supervisor@handfast.com");

		rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_PRINCIPAL, RabbitMQConfig.ROUTING_KEY_CREACION, evento);
	}

	@Override
	public void publicarCambioEstado(Incidencia incidencia, String mensajeAdicional) {
		Map<String, Object> evento = new HashMap<>();
		evento.put("incidenciaId", incidencia.getId());
		evento.put("mensaje", "Estado actualizado a " + incidencia.getEstado() + ". " + mensajeAdicional);
		evento.put("destinatario", "admin@handfast.com");

		rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_PRINCIPAL, RabbitMQConfig.ROUTING_KEY_ESTADO, evento);
	}
}
