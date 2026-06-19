package com.proyectoWeb.infraestructura.adaptadores.salida.persistencia;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MaterialRepository extends ReactiveCrudRepository<MaterialEntity, Long> {

}
