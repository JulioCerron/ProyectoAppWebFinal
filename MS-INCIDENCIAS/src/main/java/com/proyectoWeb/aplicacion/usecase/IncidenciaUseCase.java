package com.proyectoWeb.aplicacion.usecase;

import com.proyectoWeb.dominio.puertos.entrada.IncidenciaPortIn;
import com.proyectoWeb.aplicacion.dto.MaterialDTO;
import com.proyectoWeb.aplicacion.dto.ObservacionDTO;
import com.proyectoWeb.dominio.modelo.Incidencia;
import com.proyectoWeb.dominio.puertos.salida.IncidenciaEventPortOut;
import com.proyectoWeb.dominio.puertos.salida.IncidenciaRepositoryPort;
import com.proyectoWeb.infraestructura.adaptadores.salida.client.EmpleadoClient;
import com.proyectoWeb.infraestructura.adaptadores.salida.client.LogisticaClient;
import com.proyectoWeb.infraestructura.adaptadores.salida.client.SstClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class IncidenciaUseCase implements IncidenciaPortIn {
	private final IncidenciaRepositoryPort incidenciaRepositoryPort;
	private final EmpleadoClient empleadoClient;
	private final IncidenciaEventPortOut eventPortOut;
	private final SstClient sstClient;
	private final LogisticaClient logisticaClient;

	@Override
	public Incidencia registrar(Incidencia incidencia) {

		try {
			empleadoClient.obtenerEmpleadoPorId(incidencia.getReportadoPorId());
		} catch (Exception e) {
			throw new RuntimeException("El empleado que intenta reportar no existe en el sistema.");
		}

		incidencia.setEstado("REGISTRADA");
		incidencia.setFechaCreacion(LocalDateTime.now());
		Incidencia guardada = incidenciaRepositoryPort.guardar(incidencia);

		eventPortOut.publicarCreacion(guardada);

		return guardada;
	}

	@Override
	public Incidencia asignarResponsable(Long incidenciaId, Long empleadoId) {
		return incidenciaRepositoryPort.buscarPorId(incidenciaId).map(incidencia -> {

			try {
				empleadoClient.obtenerEmpleadoPorId(empleadoId);
			} catch (Exception e) {
				throw new RuntimeException("El empleado asignado no existe.");
			}

			incidencia.setAsignadoAId(empleadoId);
			incidencia.setEstado("ASIGNADA");
			return incidenciaRepositoryPort.guardar(incidencia);
		}).orElseThrow(() -> new RuntimeException("Incidencia no encontrada"));
	}

	@Override
	public Incidencia cambiarEstado(Long incidenciaId, String nuevoEstado) {

		Incidencia incidencia = incidenciaRepositoryPort.buscarPorId(incidenciaId)
				.orElseThrow(() -> new RuntimeException("Incidencia no encontrada"));

		if ("CERRADA".equals(incidencia.getEstado())) {
			throw new RuntimeException("No se puede modificar una incidencia ya cerrada.");
		}

		if ("EN_PROCESO".equals(nuevoEstado)) {
			List<ObservacionDTO> observaciones = sstClient.obtenerObservacionesPorIncidencia(incidenciaId);

			boolean tieneRiesgoAlto = observaciones.stream().anyMatch(obs -> "ALTO".equals(obs.getNivelRiesgo()));

			if (tieneRiesgoAlto) {
				throw new RuntimeException(
						"FLUJO BLOQUEADO: No se puede iniciar el trabajo. SST ha reportado un RIESGO ALTO en esta incidencia.");
			}

			if (observaciones.isEmpty()) {
				throw new RuntimeException(
						"FLUJO BLOQUEADO: Se requiere una evaluación previa de SST para pasar a EN PROCESO.");
			}
		}

		incidencia.setEstado(nuevoEstado);

		if ("CERRADA".equals(nuevoEstado)) {
			incidencia.setFechaCierre(LocalDateTime.now());
		}

		Incidencia actualizada = incidenciaRepositoryPort.guardar(incidencia);
		eventPortOut.publicarCambioEstado(actualizada, "El estado ha cambiado exitosamente a " + nuevoEstado);

		return actualizada;
	}

	@Override
	public List<Incidencia> listarTodas() {
		return incidenciaRepositoryPort.buscarTodas();
	}

	@Override
	public Incidencia obtenerPorId(Long id) {
		return incidenciaRepositoryPort.buscarPorId(id)
				.orElseThrow(() -> new RuntimeException("Incidencia con ID " + id + " no encontrada"));
	}

	public List<MaterialDTO> obtenerMaterialesDisponibles() {
		return logisticaClient.listarMateriales();
	}

	public void solicitarMateriales(Long incidenciaId, Long materialId, Integer cantidad) {
		Map<String, Object> solicitud = Map.of("incidenciaId", incidenciaId, "materialId", materialId, "cantidad",
				cantidad);
		logisticaClient.crearSolicitudMaterial(solicitud);
	}
}
