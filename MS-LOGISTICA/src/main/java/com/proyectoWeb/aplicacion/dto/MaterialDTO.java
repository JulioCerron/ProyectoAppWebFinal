package com.proyectoWeb.aplicacion.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MaterialDTO {
	private Long id;
	@NotBlank(message = "El nombre es obligatorio")
	private String nombre;
	private String descripcion;
	@PositiveOrZero(message = "El stock no puede ser negativo")
	private Integer stockActual;
	private Integer stockMinimo;
	private String unidadMedida;
}
