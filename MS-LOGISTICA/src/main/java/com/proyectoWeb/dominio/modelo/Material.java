package com.proyectoWeb.dominio.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Material {
	private Long id;
	private String nombre;
	private String descripcion;
	private Integer stockActual;
	private Integer stockMinimo;
	private String unidadMedida;
}
