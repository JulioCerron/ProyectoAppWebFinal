package com.proyectoWeb.infraestructura.adaptadores.salida.persistencia;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionRepository extends ReactiveCrudRepository<NotificacionEntity, Long> {

}
