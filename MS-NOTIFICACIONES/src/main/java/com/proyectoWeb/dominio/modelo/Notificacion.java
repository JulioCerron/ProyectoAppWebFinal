package com.proyectoWeb.dominio.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notificacion {
	private Long id;
	private Long incidenciaId;
	private String mensaje;
	private String destinatario;
	private LocalDateTime fechaEnvio;
	private String tipo;
}
