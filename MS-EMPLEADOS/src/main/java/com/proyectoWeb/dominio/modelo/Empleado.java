package com.proyectoWeb.dominio.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Empleado {
	private Long id;
	private String nombre;
	private String apellido;
	private String dni;
	private String cargo;
	private Long departamentoId;
	private Boolean activo;
	private String nombreDepartamento;
}
