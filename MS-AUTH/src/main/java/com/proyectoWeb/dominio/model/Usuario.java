package com.proyectoWeb.dominio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
	private Long id;
	private String nombreUsuario;
	private String contrasena;
	private String correo;
	private Boolean activo;
	private List<String> roles;
}