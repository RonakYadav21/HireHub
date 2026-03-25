package com.CompanyService.Messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.CompanyService.Model.JobStatusEvent;

@Service
public class JobStatusPublisher {

    private final RabbitTemplate rabbitTemplate;

    public JobStatusPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendJobStatusUpdate(Long jobId, String CompanyName, String studentEmail, String status) {

        JobStatusEvent event = new JobStatusEvent(jobId,CompanyName, studentEmail, status);

        rabbitTemplate.convertAndSend(
                RabbitConfig.JOB_STATUS_EXCHANGE,
                "", 
                event
        );

        System.out.println("Sent job status update: " + event);
    }
}