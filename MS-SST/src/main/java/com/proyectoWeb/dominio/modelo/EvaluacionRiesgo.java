package com.proyectoWeb.dominio.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvaluacionRiesgo {
	private Long id;
	private Long observacionId;
	private String nivelRiesgo;
	private String medidasPreventivas;
	private Boolean aprobado;
}
