package com.CompanyService.Messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.CompanyService.Model.ActivityEvent;

@Service
public class ActivityPublisher {

    private final RabbitTemplate rabbitTemplate;

    public ActivityPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishActivity(String type, String message) {

        ActivityEvent event = new ActivityEvent(type, message);

        rabbitTemplate.convertAndSend(
                RabbitConfig.ACTIVITY_QUEUE,
                event
        );

        System.out.println("Activity sent: " + event);
    }
}