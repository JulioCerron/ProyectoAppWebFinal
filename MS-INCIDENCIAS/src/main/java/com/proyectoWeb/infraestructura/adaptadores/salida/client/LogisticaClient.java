package com.proyectoWeb.infraestructura.adaptadores.salida.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.proyectoWeb.aplicacion.dto.MaterialDTO;

import java.util.List;

@FeignClient(name = "MS-LOGISTICA")
public interface LogisticaClient {
	@GetMapping("/logistica/inventario")
	List<MaterialDTO> listarMateriales();

	@PostMapping("/logistica/solicitudes")
	Object crearSolicitudMaterial(@RequestBody Object solicitud);
}
