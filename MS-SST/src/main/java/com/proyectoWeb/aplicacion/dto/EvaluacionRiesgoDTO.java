package com.proyectoWeb.aplicacion.dto;

import lombok.Data;

@Data
public class EvaluacionRiesgoDTO {
	private Long id;
	private Long observacionId;
	private String nivelRiesgo;
	private String medidasPreventivas;
	private Boolean aprobado;
}
