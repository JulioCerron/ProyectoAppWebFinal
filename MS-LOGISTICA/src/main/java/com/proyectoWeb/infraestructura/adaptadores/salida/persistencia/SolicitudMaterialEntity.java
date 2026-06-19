package com.proyectoWeb.infraestructura.adaptadores.salida.persistencia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("tbl_solicitudes_material")
public class SolicitudMaterialEntity {
	@Id
	private Long id;

	@Column("incidencia_id")
	private Long incidenciaId;

	@Column("material_id")
	private Long materialId;

	private Integer cantidad;
	private String estado;

	@Column("fecha_solicitud")
	private LocalDateTime fechaSolicitud;
}
