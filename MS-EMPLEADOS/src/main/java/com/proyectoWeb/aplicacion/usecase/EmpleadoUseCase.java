package com.proyectoWeb.aplicacion.usecase;

import com.proyectoWeb.dominio.modelo.Empleado;
import com.proyectoWeb.dominio.puertos.entrada.EmpleadoPortIn;
import com.proyectoWeb.dominio.puertos.salida.EmpleadoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EmpleadoUseCase implements EmpleadoPortIn {
	private final EmpleadoRepositoryPort empleadoRepositoryPort;

	@Override
	public Mono<Empleado> registrarEmpleado(Empleado empleado) {
		if (empleado.getActivo() == null) {
			empleado.setActivo(true);
		}
		return empleadoRepositoryPort.guardar(empleado);
	}

	@Override
	public Flux<Empleado> listarTodos() {
		return empleadoRepositoryPort.buscarTodos();
	}

	@Override
	public Mono<Empleado> obtenerPorId(Long id) {
		return empleadoRepositoryPort.buscarPorId(id);
	}

	@Override
	public Mono<Empleado> actualizarEmpleado(Long id, Empleado empleado) {
		return empleadoRepositoryPort.buscarPorId(id).flatMap(empleadoExistente -> {
			empleado.setId(id);
			return empleadoRepositoryPort.guardar(empleado);
		});
	}

	@Override
	public Mono<Void> eliminarEmpleado(Long id) {
		return empleadoRepositoryPort.eliminarPorId(id);
	}

	@Override
	public Flux<Empleado> listarPorDepartamento(Long departamentoId) {
		return empleadoRepositoryPort.buscarPorDepartamento(departamentoId);
	}

	@Override
	public Flux<Empleado> listarPorCargo(String cargo) {
		return empleadoRepositoryPort.buscarPorCargo(cargo);
	}
}
