package com.proyectoWeb.dominio.puertos.entrada;

import com.proyectoWeb.dominio.modelo.Empleado;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmpleadoPortIn {
	Mono<Empleado> registrarEmpleado(Empleado empleado);

	Flux<Empleado> listarTodos();

	Mono<Empleado> obtenerPorId(Long id);

	Mono<Empleado> actualizarEmpleado(Long id, Empleado empleado);

	Mono<Void> eliminarEmpleado(Long id);

	Flux<Empleado> listarPorDepartamento(Long departamentoId);

	Flux<Empleado> listarPorCargo(String cargo);
}
