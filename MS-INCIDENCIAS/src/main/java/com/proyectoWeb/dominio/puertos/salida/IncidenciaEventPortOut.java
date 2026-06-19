package com.proyectoWeb.dominio.puertos.salida;

import com.proyectoWeb.dominio.modelo.Incidencia;

public interface IncidenciaEventPortOut {
	void publicarCreacion(Incidencia incidencia);

	void publicarCambioEstado(Incidencia incidencia, String mensaje);
}
