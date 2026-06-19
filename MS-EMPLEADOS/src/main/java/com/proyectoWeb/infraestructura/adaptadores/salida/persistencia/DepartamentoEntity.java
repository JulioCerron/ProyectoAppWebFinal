package com.proyectoWeb.infraestructura.adaptadores.salida.persistencia;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;

@Data
@Table("tbl_departamentos")
public class DepartamentoEntity {
	@Id
	private Long id;
	private String nombre;
}
