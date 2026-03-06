package com.PlacementService.Messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.PlacementService.Model.ActivityEvent;

@Component
public class EventActivityPublisher {
	  private final RabbitTemplate rabbitTemplate;

	    public EventActivityPublisher(RabbitTemplate rabbitTemplate) {
	        this.rabbitTemplate = rabbitTemplate;
	    }
	    
	    public void publishActivity(String type, String message) {

	        ActivityEvent event = new ActivityEvent(type, message);

	        rabbitTemplate.convertAndSend(
	                RabbitConfig.ACTIVITY_QUEUE,
	                event
	        );

	    }
}
