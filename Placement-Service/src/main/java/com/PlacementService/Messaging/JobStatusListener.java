package com.PlacementService.Messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.PlacementService.Dto.JobStatusEvent;
import com.PlacementService.Model.JobApplication;
import com.PlacementService.Repository.JobApplicationRepository;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

@Service
public class JobStatusListener {

    private final JobApplicationRepository jobApplicationRepository;

    public JobStatusListener(JobApplicationRepository jobApplicationRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
    }

    @RabbitListener(queues = "placement-status-queue", ackMode = "MANUAL")
    public void handleJobStatusEvent(
            @Payload JobStatusEvent event,
            Message message,
            Channel channel) throws Exception {

        try {

            System.out.println("Placement received job status update: " + event);

            // Find application
            JobApplication application = jobApplicationRepository
                    .findByStudentEmailAndJobId(
                            event.getStudentEmail(),
                            event.getJobId()
                    )
                    .orElse(null);

            if (application != null) {

                application.setStatus(event.getApplication_status());
                jobApplicationRepository.save(application);

                System.out.println("Application status updated successfully");

            } else {
                System.out.println("Application not found");
            }

            // ACK message
            channel.basicAck(
                    message.getMessageProperties().getDeliveryTag(),
                    false
            );

        } catch (Exception ex) {

            System.out.println("Error processing message: " + ex.getMessage());

            // NACK and requeue
            channel.basicNack(
                    message.getMessageProperties().getDeliveryTag(),
                    false,
                    true
            );
        }
    }
}