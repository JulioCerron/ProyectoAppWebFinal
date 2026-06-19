package com.proyectoWeb.aplicacion.usecase;

import com.proyectoWeb.dominio.modelo.Material;
import com.proyectoWeb.dominio.modelo.SolicitudMaterial;
import com.proyectoWeb.dominio.puertos.entrada.GestionLogisticaPortIn;
import com.proyectoWeb.dominio.puertos.salida.LogisticaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GestionLogisticaUseCase implements GestionLogisticaPortIn {
	private final LogisticaRepositoryPort repositoryPort;

	@Override
	public Mono<Material> registrarMaterial(Material material) {
		return repositoryPort.guardarMaterial(material);
	}

	@Override
	public Flux<Material> listarInventario() {
		return repositoryPort.buscarMaterialesTodos();
	}

	@Override
	public Mono<SolicitudMaterial> crearSolicitud(SolicitudMaterial solicitud) {
		solicitud.setEstado("PENDIENTE");
		return repositoryPort.guardarSolicitud(solicitud);
	}

	@Override
	public Mono<SolicitudMaterial> atenderSolicitud(Long solicitudId, String nuevoEstado) {
		return repositoryPort.buscarSolicitudesTodas().filter(s -> s.getId().equals(solicitudId)).next()
				.flatMap(solicitud -> {
					if (nuevoEstado.equals("ENTREGADO")) {
						return descontarStock(solicitud.getMaterialId(), solicitud.getCantidad())
								.then(actualizarEstadoSolicitud(solicitud, "ENTREGADO"));
					}
					return actualizarEstadoSolicitud(solicitud, nuevoEstado);
				});
	}

	private Mono<Void> descontarStock(Long materialId, Integer cantidad) {
		return repositoryPort.buscarMaterialPorId(materialId).flatMap(m -> {
			if (m.getStockActual() < cantidad) {
				return Mono.error(new RuntimeException("Stock insuficiente"));
			}
			m.setStockActual(m.getStockActual() - cantidad);
			return repositoryPort.guardarMaterial(m);
		}).then();
	}

	private Mono<SolicitudMaterial> actualizarEstadoSolicitud(SolicitudMaterial s, String estado) {
		s.setEstado(estado);
		return repositoryPort.guardarSolicitud(s);
	}
}
