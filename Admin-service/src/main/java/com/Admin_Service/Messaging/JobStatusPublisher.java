package com.Admin_Service.Messaging;



import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.Admin_Service.Model.CompanyStatusEvent;



@Service
public class JobStatusPublisher {

    private final RabbitTemplate rabbitTemplate;

    public JobStatusPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendJobStatusUpdate(Long compnayId, String status) {
    	CompanyStatusEvent event = new CompanyStatusEvent(compnayId, status);
        rabbitTemplate.convertAndSend(RabbitConfig.JOB_STATUS_QUEUE, event);
        System.out.println("Sent job status update: " + event);
    }

}
