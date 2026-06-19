package com.proyectoWeb.infraestructura.configuracion;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	public static final String COLA_NOTIFICACIONES = "cola.notificaciones";
	public static final String EXCHANGE_PRINCIPAL = "exchange.handfast";
	public static final String ROUTING_KEY = "incidencia.eventos.#";

	@Bean
	public Queue cola() {
		return new Queue(COLA_NOTIFICACIONES, true);
	}

	@Bean
	public TopicExchange exchange() {
		return new TopicExchange(EXCHANGE_PRINCIPAL);
	}

	@Bean
	public Binding binding(Queue cola, TopicExchange exchange) {
		return BindingBuilder.bind(cola).to(exchange).with(ROUTING_KEY);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
