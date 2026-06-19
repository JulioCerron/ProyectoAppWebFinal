package com.proyectoWeb.aplicacion.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SolicitudMaterialDTO {
	private Long id;
	private Long incidenciaId;
	private Long materialId;
	private String nombreMaterial;
	private Integer cantidad;
	private String estado;
	private LocalDateTime fechaSolicitud;
}
