package com.proyectoWeb.infraestructura.adaptadores.salida.persistencia;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("tbl_observaciones")
public class ObservacionEntity {
	@Id
	private Long id;
	@Column("incidencia_id")
	private Long incidenciaId;
	private String descripcion;
	@Column("fecha_registro")
	private LocalDateTime fechaRegistro;
	@Column("observado_por")
	private String observadoPor;
}
