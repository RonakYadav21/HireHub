package com.CompanyService.Messaging;




import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.CompanyService.Model.Company;
import com.CompanyService.Model.CompanyStatusEvent;
import com.CompanyService.Repository.CompanyRepository;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.messaging.handler.annotation.Payload;


@Service
public class JobStatusListener {

    private final CompanyRepository CompanyApplicationRepository;

    public JobStatusListener(CompanyRepository CompanyApplicationRepository) {
        this.CompanyApplicationRepository = CompanyApplicationRepository;
    }

    @RabbitListener(queues = RabbitConfig.COMPANY_QUEUE, ackMode = "MANUAL")
    public void handleJobStatusEvent(  @Payload CompanyStatusEvent event , Message message,
            Channel channel) throws Exception {
    	 try {
//        System.out.println("Received company status update: " + event);

        // Update status in Placement DB
        Company application = CompanyApplicationRepository
                .findById(event.getCompanyId())
                .orElse(null);

        if (application != null) {
            application.setStatus(event.getStatus());
            CompanyApplicationRepository.save(application);
            // Send ACK when successful
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }catch (Exception ex) {
        System.out.println("Error: " + ex.getMessage());

        // ❌ NACK and requeue
        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);

        // Or reject without requeue
        // channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
    }
    	 
}
}
