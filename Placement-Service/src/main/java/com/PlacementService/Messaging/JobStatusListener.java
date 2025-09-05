package com.PlacementService.Messaging;



import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.PlacementService.Dto.JobStatusEvent;
import com.PlacementService.Model.JobApplication;
import com.PlacementService.Repository.JobApplicationRepository;

@Service
public class JobStatusListener {

    private final JobApplicationRepository jobApplicationRepository;

    public JobStatusListener(JobApplicationRepository jobApplicationRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
    }

    @RabbitListener(queues = RabbitConfig.JOB_STATUS_QUEUE)
    public void handleJobStatusEvent(JobStatusEvent event) {
        System.out.println("Received job status update: " + event);

        // Update status in Placement DB
        JobApplication application = jobApplicationRepository
                .findByStudentEmailAndJobId(event.getStudentEmail(), event.getJobId())
                .orElse(null);

        if (application != null) {
            application.setStatus(event.getStatus());
            jobApplicationRepository.save(application);
        }
    }
}
