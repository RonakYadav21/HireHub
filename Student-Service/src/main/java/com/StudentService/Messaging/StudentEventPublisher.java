package com.StudentService.Messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.StudentService.Model.ActivityEvent;
@Component
public class StudentEventPublisher {
	
	  private final RabbitTemplate rabbitTemplate;

	    public StudentEventPublisher(RabbitTemplate rabbitTemplate) {
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
