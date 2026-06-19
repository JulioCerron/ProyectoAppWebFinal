package com.proyectoWeb.infraestructura.adaptadores.entrada.web;

import com.proyectoWeb.aplicacion.dto.EmpleadoDTO;
import com.proyectoWeb.dominio.modelo.Empleado;
import com.proyectoWeb.dominio.puertos.entrada.EmpleadoPortIn;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@Tag(name = "Gestión de Empleados", description = "Endpoints para el CRUD de personal")
public class EmpleadoController {
	private final EmpleadoPortIn empleadoPortIn;

	@PostMapping
	@Operation(summary = "Registrar un nuevo empleado")
	public Mono<EmpleadoDTO> crear(@Valid @RequestBody EmpleadoDTO dto) {
		return empleadoPortIn.registrarEmpleado(mapearADominio(dto)).map(this::mapearADto);
	}

	@GetMapping
	@Operation(summary = "Listar todos los empleados")
	public Flux<EmpleadoDTO> listar() {
		return empleadoPortIn.listarTodos().map(this::mapearADto);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtener empleado por su ID")
	public Mono<EmpleadoDTO> obtenerPorId(@PathVariable Long id) {
		return empleadoPortIn.obtenerPorId(id).map(this::mapearADto);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Actualizar datos de un empleado")
	public Mono<EmpleadoDTO> actualizar(@PathVariable Long id, @RequestBody EmpleadoDTO dto) {
		return empleadoPortIn.actualizarEmpleado(id, mapearADominio(dto)).map(this::mapearADto);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Eliminar un empleado")
	public Mono<Void> eliminar(@PathVariable Long id) {
		return empleadoPortIn.eliminarEmpleado(id);
	}

	@GetMapping("/departamento/{id}")
	@Operation(summary = "Filtrar empleados por área/departamento")
	public Flux<EmpleadoDTO> listarPorArea(@PathVariable Long id) {
		return empleadoPortIn.listarPorDepartamento(id).map(this::mapearADto);
	}

	@GetMapping("/cargo/{cargo}")
	@Operation(summary = "Filtrar empleados por cargo")
	public Flux<EmpleadoDTO> listarPorCargo(@PathVariable String cargo) {
		return empleadoPortIn.listarPorCargo(cargo).map(this::mapearADto);
	}

	private Empleado mapearADominio(EmpleadoDTO dto) {
		return Empleado.builder().id(dto.getId()).nombre(dto.getNombre()).apellido(dto.getApellido()).dni(dto.getDni())
				.cargo(dto.getCargo()).departamentoId(dto.getDepartamentoId()).activo(dto.getActivo()).build();
	}

	private EmpleadoDTO mapearADto(Empleado dominio) {
		EmpleadoDTO dto = EmpleadoDTO.builder().id(dominio.getId()).nombre(dominio.getNombre())
				.apellido(dominio.getApellido()).dni(dominio.getDni()).cargo(dominio.getCargo())
				.departamentoId(dominio.getDepartamentoId()).nombreDepartamento(dominio.getNombreDepartamento())
				.activo(dominio.getActivo()).build();
		return dto;
	}
}