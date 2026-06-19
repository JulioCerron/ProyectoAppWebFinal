package com.proyectoWeb.infraestructura.configuracion;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI incidenciasOpenAPI() {
		return new OpenAPI().info(new Info().title("API del Núcleo de Incidencias - HANDFAST").version("1.0")
				.description("Microservicio central para la orquestación y seguimiento de eventos operativos."));
	}
}
