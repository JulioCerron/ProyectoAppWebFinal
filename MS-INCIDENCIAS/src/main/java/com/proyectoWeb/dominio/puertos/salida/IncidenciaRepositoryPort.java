package com.proyectoWeb.dominio.puertos.salida;

import com.proyectoWeb.dominio.modelo.Incidencia;
import java.util.List;
import java.util.Optional;

public interface IncidenciaRepositoryPort {
	Incidencia guardar(Incidencia incidencia);

	List<Incidencia> buscarTodas();

	Optional<Incidencia> buscarPorId(Long id);

	List<Incidencia> buscarPorEstado(String estado);
}
