package com.proyectoWeb.dominio.puertos.entrada;

import com.proyectoWeb.dominio.modelo.Material;
import com.proyectoWeb.dominio.modelo.SolicitudMaterial;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GestionLogisticaPortIn {
	Mono<Material> registrarMaterial(Material material);

	Flux<Material> listarInventario();

	Mono<SolicitudMaterial> crearSolicitud(SolicitudMaterial solicitud);

	Mono<SolicitudMaterial> atenderSolicitud(Long solicitudId, String nuevoEstado);
}
