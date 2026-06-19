package com.proyectoWeb.infraestructura.adaptadores.salida.persistencia;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("tbl_evaluaciones_riesgo")
public class EvaluacionRiesgoEntity {
	@Id
	private Long id;
	@Column("observacion_id")
	private Long observacionId;
	@Column("nivel_riesgo")
	private String nivelRiesgo;
	@Column("medidas_preventivas")
	private String medidasPreventivas;
	private Boolean aprobado;
}
