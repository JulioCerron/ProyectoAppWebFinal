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
@Table("tbl_notificaciones_historial")
public class NotificacionEntity {
	@Id
	private Long id;
	@Column("incidencia_id")
	private Long incidenciaId;
	private String mensaje;
	private String destinatario;
	@Column("fecha_envio")
	private LocalDateTime fechaEnvio;
	private String tipo;
}
