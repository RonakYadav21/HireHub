package com.CompanyService.Messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String JOB_STATUS_QUEUE = "job-status-queue";
    public static final String COMPANY_STATUS_QUEUE = "Compnay-status-queue";
    public static final String ACTIVITY_QUEUE = "activity-queue";


    @Bean
    public Queue jobStatusQueue() {
        return new Queue(JOB_STATUS_QUEUE, true);
    }

    @Bean
    public Queue CompanyStatusQueue() {
        return new Queue(COMPANY_STATUS_QUEUE, true);
    }
    @Bean
    public Queue activityQueue() {
        return new Queue(ACTIVITY_QUEUE, true);
    }
    
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter()); // 👈 use JSON
        return rabbitTemplate;
    }
}
