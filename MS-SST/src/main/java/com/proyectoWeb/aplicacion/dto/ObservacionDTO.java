package com.proyectoWeb.aplicacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ObservacionDTO {
	private Long id;
	@NotNull(message = "El ID de incidencia es obligatorio")
	private Long incidenciaId;
	@NotBlank(message = "La descripción no puede estar vacía")
	private String descripcion;
	private LocalDateTime fechaRegistro;
	private String observadoPor;
	private String nivelRiesgo;
}
