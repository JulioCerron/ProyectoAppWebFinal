package com.proyectoWeb.aplicacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IncidenciaDTO {
	private Long id;

	@NotBlank(message = "El título es obligatorio")
	private String titulo;

	@NotBlank(message = "La descripción es obligatoria")
	private String descripcion;

	@NotBlank(message = "La prioridad es obligatoria")
	private String prioridad;

	private String estado;

	@NotNull(message = "El ID del área es obligatorio")
	private Long areaId;

	@NotNull(message = "El ID del empleado que reporta es obligatorio")
	private Long reportadoPorId;

	private Long asignadoAId;
	private LocalDateTime fechaCreacion;
	private LocalDateTime fechaCierre;
}
