package com.proyectoWeb.infraestructura.configuracion;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info().title("API de Empleados HANDFAST").version("1.0")
				.description("Servicio para la gestión de personal operativo"));
	}
}
