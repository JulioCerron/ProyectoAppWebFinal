package com.proyectoWeb.infraestructura.configuracion;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI sstOpenAPI() {
		return new OpenAPI().info(new Info().title("API de Seguridad y Salud en el Trabajo - HANDFAST").version("1.0")
				.description("Microservicio encargado del registro de observaciones de seguridad, "
						+ "evaluación de riesgos operativos y seguimiento de medidas preventivas."));
	}
}
