package com.proyectoWeb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsIncidenciasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsIncidenciasApplication.class, args);
	}

}
