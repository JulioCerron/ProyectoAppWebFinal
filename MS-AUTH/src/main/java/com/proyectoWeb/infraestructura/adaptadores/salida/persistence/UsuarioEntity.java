package com.proyectoWeb.infraestructura.adaptadores.salida.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("tbl_usuarios")
public class UsuarioEntity {
	@Id
	private Long id;

	@Column("nombre_usuario")
	private String nombreUsuario;

	@Column("contrasena")
	private String contrasena;

	private String correo;
	private Boolean activo;
}