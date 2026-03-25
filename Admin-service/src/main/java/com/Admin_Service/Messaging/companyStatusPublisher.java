package com.Admin_Service.Messaging;



import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.Admin_Service.Model.CompanyStatusEvent;



@Service
public class companyStatusPublisher {

    private final RabbitTemplate rabbitTemplate;

    public companyStatusPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendcompanyStatusUpdate(Long compnayId,String email, String status) {
    	CompanyStatusEvent event = new CompanyStatusEvent(compnayId,email, status);
    	
    	 rabbitTemplate.convertAndSend(
                 RabbitConfig.COMPANY_STATUS_EXCHANGE,
                 RabbitConfig.ROUTING_KEY,
                 event
         );        System.out.println("Sent job status update: " + event);
    }

}
