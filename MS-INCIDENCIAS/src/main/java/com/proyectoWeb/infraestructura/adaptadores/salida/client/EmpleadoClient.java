package com.proyectoWeb.infraestructura.adaptadores.salida.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MS-EMPLEADOS")
public interface EmpleadoClient {
	@GetMapping("/employees/{id}")
	Object obtenerEmpleadoPorId(@PathVariable("id") Long id);
}
