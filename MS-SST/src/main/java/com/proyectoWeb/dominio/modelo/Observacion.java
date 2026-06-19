package com.proyectoWeb.dominio.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Observacion {
	private Long id;
	private Long incidenciaId;
	private String descripcion;
	private LocalDateTime fechaRegistro;
	private String observadoPor;
	private String nivelRiesgo;
}
