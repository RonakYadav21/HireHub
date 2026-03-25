package com.PlacementService.Messaging;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.PlacementService.Dto.JobStatusEvent;

@Service
public class JobStatusPublisher {

    private final RabbitTemplate rabbitTemplate;

    public JobStatusPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishJobStatus(String studentEmail,
                                 String companyName,
                                 Long jobId,
                                 String status) {

    	JobStatusEvent event = new JobStatusEvent(
                studentEmail,
                companyName,
                jobId,
                status
        );

        rabbitTemplate.convertAndSend(
        		RabbitConfig.JOB_STATUS_QUEUE,
                event
        );

        System.out.println("Placement service published: " + event);
    }
}