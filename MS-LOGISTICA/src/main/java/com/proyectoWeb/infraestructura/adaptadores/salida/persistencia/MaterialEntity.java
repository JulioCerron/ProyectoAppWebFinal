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
@Table("tbl_materiales")
public class MaterialEntity {
	@Id
	private Long id;
	private String nombre;
	private String descripcion;

	@Column("stock_actual")
	private Integer stockActual;

	@Column("stock_minimo")
	private Integer stockMinimo;

	@Column("unidad_medida")
	private String unidadMedida;
}
