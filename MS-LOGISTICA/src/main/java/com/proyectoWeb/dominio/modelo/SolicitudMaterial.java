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
public class SolicitudMaterial {
	private Long id;
	private Long incidenciaId;
	private Long materialId;
	private Integer cantidad;
	private String estado;
	private LocalDateTime fechaSolicitud;
	private String nombreMaterial;
}
