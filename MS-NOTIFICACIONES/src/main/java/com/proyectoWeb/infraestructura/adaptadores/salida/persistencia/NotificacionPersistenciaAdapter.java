package com.proyectoWeb.infraestructura.adaptadores.salida.persistencia;

import com.proyectoWeb.dominio.modelo.Notificacion;
import com.proyectoWeb.dominio.puertos.salida.NotificacionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class NotificacionPersistenciaAdapter implements NotificacionRepositoryPort {
	private final NotificacionRepository repository;

	@Override
	public Mono<Notificacion> guardar(Notificacion n) {
		NotificacionEntity e = NotificacionEntity.builder().incidenciaId(n.getIncidenciaId()).mensaje(n.getMensaje())
				.destinatario(n.getDestinatario()).fechaEnvio(n.getFechaEnvio()).tipo(n.getTipo()).build();

		return repository.save(e)
				.map(ent -> Notificacion.builder().id(ent.getId()).incidenciaId(ent.getIncidenciaId())
						.mensaje(ent.getMensaje()).destinatario(ent.getDestinatario()).fechaEnvio(ent.getFechaEnvio())
						.tipo(ent.getTipo()).build());
	}
}
