package com.proyectoWeb.infraestructura.adaptadores.salida.persistencia;

import com.proyectoWeb.dominio.modelo.Empleado;
import com.proyectoWeb.dominio.puertos.salida.EmpleadoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class EmpleadoPersistenciaAdapter implements EmpleadoRepositoryPort {
	private final EmpleadoRepository repository;
	private final DepartamentoRepository departamentoRepository;

	@Override
	public Mono<Empleado> guardar(Empleado empleado) {
		return repository.save(mapToEntity(empleado)).map(this::mapToDomain);
	}

	@Override
	public Flux<Empleado> buscarTodos() {
		return repository.findAll().flatMap(this::enriquecerConDepartamento);
	}

	@Override
	public Mono<Empleado> buscarPorId(Long id) {
		return repository.findById(id).map(this::mapToDomain);
	}

	@Override
	public Mono<Void> eliminarPorId(Long id) {
		return repository.deleteById(id);
	}

	@Override
	public Mono<Empleado> buscarPorDni(String dni) {
		return repository.findByDni(dni).map(this::mapToDomain);
	}

	@Override
	public Flux<Empleado> buscarPorDepartamento(Long departamentoId) {
		return repository.findByDepartamentoId(departamentoId).flatMap(this::enriquecerConDepartamento);
	}

	@Override
	public Flux<Empleado> buscarPorCargo(String cargo) {
		return repository.findByCargo(cargo).flatMap(this::enriquecerConDepartamento);
	}

	private Mono<Empleado> enriquecerConDepartamento(EmpleadoEntity entity) {
		return departamentoRepository.findById(entity.getDepartamentoId()).map(dep -> {
			Empleado dom = mapToDomain(entity);
			dom.setNombreDepartamento(dep.getNombre());
			return dom;
		}).defaultIfEmpty(mapToDomain(entity));
	}

	private Empleado mapToDomain(EmpleadoEntity entity) {
		return Empleado.builder().id(entity.getId()).nombre(entity.getNombre()).apellido(entity.getApellido())
				.dni(entity.getDni()).cargo(entity.getCargo()).departamentoId(entity.getDepartamentoId())
				.activo(entity.getActivo()).build();
	}

	private EmpleadoEntity mapToEntity(Empleado domain) {
		return EmpleadoEntity.builder().id(domain.getId()).nombre(domain.getNombre()).apellido(domain.getApellido())
				.dni(domain.getDni()).cargo(domain.getCargo()).departamentoId(domain.getDepartamentoId())
				.activo(domain.getActivo()).build();
	}
}
