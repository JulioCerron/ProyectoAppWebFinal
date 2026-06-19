package com.proyectoWeb.aplicacion.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoDTO {
	private Long id;

	@NotBlank(message = "El nombre es obligatorio")
	private String nombre;

	@NotBlank(message = "El apellido es obligatorio")
	private String apellido;

	@Size(min = 8, max = 8, message = "El DNI debe tener 8 caracteres")
	private String dni;
	private String cargo;

	@NotNull(message = "El ID de departamento es obligatorio")
	private Long departamentoId;
	private String nombreDepartamento;
	private Boolean activo;
}
