package com.proyectoWeb.infraestructura.configuracion;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI logisticaOpenAPI() {
		return new OpenAPI().info(new Info().title("API de Logística e Inventario - HANDFAST").version("1.0")
				.description("Gestión de materiales y solicitudes de repuestos para incidencias operativas"));
	}
}
