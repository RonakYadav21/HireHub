package com.CompanyService.Messaging;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String JOB_STATUS_EXCHANGE = "job-status-exchange";
    public static final String COMPANY_STATUS_EXCHANGE = "company-status-exchange";




    public static final String PLACEMENT_STATUS_QUEUE = "placement-status-queue";
    public static final String STUDENT_STATUS_QUEUE = "student-status-queue";

    public static final String COMPANY_QUEUE = "company-status-queue";
    public static final String ACTIVITY_QUEUE = "activity-queue";
    public static final String ROUTING_KEY = "company.status";


    // ----------- QUEUES -----------

    @Bean
    public Queue placementStatusQueue() {
        return new Queue(PLACEMENT_STATUS_QUEUE, true);
    }

    @Bean
    public Queue studentStatusQueue() {
        return new Queue(STUDENT_STATUS_QUEUE, true);
    }

    @Bean
    public Queue companyStatusQueue() {
        return new Queue(COMPANY_QUEUE, true);
    }

    @Bean
    public Queue activityQueue() {
        return new Queue(ACTIVITY_QUEUE, true);
    }


    // ----------- FANOUT EXCHANGE -----------

    @Bean
    public FanoutExchange jobStatusExchange() {
        return new FanoutExchange(JOB_STATUS_EXCHANGE);
    }
    @Bean
    public DirectExchange companyStatusExchange() {
        return new DirectExchange(COMPANY_STATUS_EXCHANGE);
    }


    // ----------- BINDINGS -----------

    @Bean
    public Binding placementBinding() {
        return BindingBuilder
                .bind(placementStatusQueue())
                .to(jobStatusExchange());
    }

    @Bean
    public Binding studentBinding() {
        return BindingBuilder
                .bind(studentStatusQueue())
                .to(jobStatusExchange());
    }
    @Bean
    public Binding companyStatusBinding() {
        return BindingBuilder
                .bind(companyStatusQueue())
                .to(companyStatusExchange())
                .with(ROUTING_KEY);
    }

    // ----------- MESSAGE CONVERTER -----------

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