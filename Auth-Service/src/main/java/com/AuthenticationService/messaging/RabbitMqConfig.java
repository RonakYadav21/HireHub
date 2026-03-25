package com.AuthenticationService.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

	 public static final String COMPANY_STATUS_EXCHANGE = "company-status-exchange";
	    public static final String AUTH_QUEUE = "auth-company-status-queue";
	    public static final String ROUTING_KEY = "company.status";


    @Bean
    public Queue companyStatusQueue() {
        return new Queue(AUTH_QUEUE, true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(COMPANY_STATUS_EXCHANGE);
    }

    @Bean
    public Binding binding(Queue companyStatusQueue, DirectExchange exchange) {
        return BindingBuilder
                .bind(companyStatusQueue)
                .to(exchange)
                .with(ROUTING_KEY);
    }
    
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    // ----------- RABBIT TEMPLATE -----------

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());

        return rabbitTemplate;
    }
}