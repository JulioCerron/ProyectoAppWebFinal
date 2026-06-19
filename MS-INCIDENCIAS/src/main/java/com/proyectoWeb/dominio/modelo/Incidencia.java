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
public class Incidencia {
	private Long id;
	private String titulo;
	private String descripcion;
	private String prioridad;
	private String estado;
	private Long areaId;
	private Long reportadoPorId;
	private Long asignadoAId;
	private LocalDateTime fechaCreacion;
	private LocalDateTime fechaCierre;
}
