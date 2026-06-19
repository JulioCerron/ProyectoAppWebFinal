package com.proyectoWeb.dominio.puertos.salida;

import com.proyectoWeb.dominio.modelo.Empleado;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmpleadoRepositoryPort {
	Mono<Empleado> guardar(Empleado empleado);

	Flux<Empleado> buscarTodos();

	Mono<Empleado> buscarPorId(Long id);

	Mono<Void> eliminarPorId(Long id);

	Mono<Empleado> buscarPorDni(String dni);

	Flux<Empleado> buscarPorDepartamento(Long departamentoId);

	Flux<Empleado> buscarPorCargo(String cargo);
}
