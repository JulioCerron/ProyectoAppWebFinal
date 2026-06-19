package com.proyectoWeb.infraestructura.configuracion;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
	public static final String EXCHANGE_PRINCIPAL = "exchange.handfast";
	public static final String ROUTING_KEY_CREACION = "incidencia.eventos.creada";
	public static final String ROUTING_KEY_ESTADO = "incidencia.eventos.estado";

	@Bean
	public TopicExchange exchange() {
		return new TopicExchange(EXCHANGE_PRINCIPAL);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
