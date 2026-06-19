package com.proyectoWeb.infraestructura.adaptadores.salida.persistencia;

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
@Table("tbl_empleados")
public class EmpleadoEntity {
	@Id
	private Long id;
	private String nombre;
	private String apellido;
	private String dni;
	private String cargo;

	@Column("departamento_id")
	private Long departamentoId;

	private Boolean activo;
}
