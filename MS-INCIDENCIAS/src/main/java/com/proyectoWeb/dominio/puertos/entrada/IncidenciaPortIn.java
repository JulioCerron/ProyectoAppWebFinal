package com.proyectoWeb.dominio.puertos.entrada;

import com.proyectoWeb.dominio.modelo.Incidencia;
import java.util.List;

public interface IncidenciaPortIn {
	Incidencia registrar(Incidencia incidencia);

	Incidencia asignarResponsable(Long incidenciaId, Long empleadoId);

	Incidencia cambiarEstado(Long incidenciaId, String nuevoEstado);

	List<Incidencia> listarTodas();

	Incidencia obtenerPorId(Long id);
}
