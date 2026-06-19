package com.proyectoWeb.infraestructura.adaptadores.salida.persistencia;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_incidencias")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IncidenciaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 100)
	private String titulo;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String descripcion;

	@Column(nullable = false, length = 20)
	private String prioridad;

	@Column(length = 20)
	private String estado;

	@Column(name = "area_id")
	private Long areaId;

	@Column(name = "reportado_por_id")
	private Long reportadoPorId;

	@Column(name = "asignado_a_id")
	private Long asignadoAId;

	@Column(name = "fecha_creacion")
	private LocalDateTime fechaCreacion;

	@Column(name = "fecha_cierre")
	private LocalDateTime fechaCierre;

	@PrePersist
	protected void onCreate() {
		this.fechaCreacion = LocalDateTime.now();
		if (this.estado == null) {
			this.estado = "REGISTRADA";
		}
	}
}
