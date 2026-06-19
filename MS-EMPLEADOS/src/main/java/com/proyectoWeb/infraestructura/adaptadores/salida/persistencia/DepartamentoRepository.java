package com.proyectoWeb.infraestructura.adaptadores.salida.persistencia;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DepartamentoRepository extends ReactiveCrudRepository<DepartamentoEntity, Long> {

}
