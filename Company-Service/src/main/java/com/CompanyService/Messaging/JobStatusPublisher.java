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

    public void sendJobStatusUpdate(Long jobId, String studentEmail, String status) {
        JobStatusEvent event = new JobStatusEvent(jobId, studentEmail, status);
        rabbitTemplate.convertAndSend(RabbitConfig.JOB_STATUS_QUEUE, event);
        System.out.println("Sent job status update: " + event);
    }
}
