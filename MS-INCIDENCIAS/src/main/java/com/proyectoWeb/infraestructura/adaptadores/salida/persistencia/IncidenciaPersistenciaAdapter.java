package com.proyectoWeb.infraestructura.adaptadores.salida.persistencia;

import com.proyectoWeb.dominio.modelo.Incidencia;
import com.proyectoWeb.dominio.puertos.salida.IncidenciaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class IncidenciaPersistenciaAdapter implements IncidenciaRepositoryPort {
	private final IncidenciaRepository repository;

	@Override
	public Incidencia guardar(Incidencia d) {
		IncidenciaEntity entity = repository.save(mapearAEntity(d));
		return mapearADominio(entity);
	}

	@Override
	public List<Incidencia> buscarTodas() {
		return repository.findAll().stream().map(this::mapearADominio).collect(Collectors.toList());
	}

	@Override
	public Optional<Incidencia> buscarPorId(Long id) {
		return repository.findById(id).map(this::mapearADominio);
	}

	@Override
	public List<Incidencia> buscarPorEstado(String estado) {
		return repository.findByEstado(estado).stream().map(this::mapearADominio).collect(Collectors.toList());
	}

	private Incidencia mapearADominio(IncidenciaEntity e) {
		return Incidencia.builder().id(e.getId()).titulo(e.getTitulo()).descripcion(e.getDescripcion())
				.prioridad(e.getPrioridad()).estado(e.getEstado()).areaId(e.getAreaId())
				.reportadoPorId(e.getReportadoPorId()).asignadoAId(e.getAsignadoAId())
				.fechaCreacion(e.getFechaCreacion()).fechaCierre(e.getFechaCierre()).build();
	}

	private IncidenciaEntity mapearAEntity(Incidencia d) {
		return IncidenciaEntity.builder().id(d.getId()).titulo(d.getTitulo()).descripcion(d.getDescripcion())
				.prioridad(d.getPrioridad()).estado(d.getEstado()).areaId(d.getAreaId())
				.reportadoPorId(d.getReportadoPorId()).asignadoAId(d.getAsignadoAId())
				.fechaCreacion(d.getFechaCreacion()).fechaCierre(d.getFechaCierre()).build();
	}
}
