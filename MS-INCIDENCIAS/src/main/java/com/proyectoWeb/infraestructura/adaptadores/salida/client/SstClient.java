package com.proyectoWeb.infraestructura.adaptadores.salida.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.proyectoWeb.aplicacion.dto.ObservacionDTO;

import java.util.List;

@FeignClient(name = "MS-SST")
public interface SstClient {

	@GetMapping("/sst/incidencia/{id}/observaciones")
	List<ObservacionDTO> obtenerObservacionesPorIncidencia(@PathVariable("id") Long id);

}
