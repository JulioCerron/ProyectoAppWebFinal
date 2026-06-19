package com.proyectoWeb.infraestructura.adaptadores.salida.persistencia;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IncidenciaRepository extends JpaRepository<IncidenciaEntity, Long> {
	List<IncidenciaEntity> findByEstado(String estado);
}
