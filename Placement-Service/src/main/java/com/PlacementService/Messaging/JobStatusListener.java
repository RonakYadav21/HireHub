package com.PlacementService.Messaging;



import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.PlacementService.Dto.JobStatusEvent;
import com.PlacementService.Model.JobApplication;
import com.PlacementService.Repository.JobApplicationRepository;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;


@Service
public class JobStatusListener {

    private final JobApplicationRepository jobApplicationRepository;

    public JobStatusListener(JobApplicationRepository jobApplicationRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
    }

    @RabbitListener(queues = RabbitConfig.JOB_STATUS_QUEUE, ackMode = "MANUAL")
    public void handleJobStatusEvent(  @Payload JobStatusEvent event , Message message,
            Channel channel) throws Exception {
    	 try {
        System.out.println("Received job status update: " + event);

        // Update status in Placement DB
        JobApplication application = jobApplicationRepository
                .findByStudentEmailAndJobId(event.getStudentEmail(), event.getJobId())
                .orElse(null);

        if (application != null) {
            application.setStatus(event.getStatus());
            jobApplicationRepository.save(application);
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
