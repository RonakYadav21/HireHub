package com.StudentService.Messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.StudentService.Model.JobStatusEvent;
import com.StudentService.Service.NotificationService;


@Service
public class JobStatusListener {

    private final NotificationService notificationService;

    public JobStatusListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "student-status-queue")
    public void receiveJobStatus(JobStatusEvent event) {

        System.out.println("Student service received: " + event);

        notificationService.createNotification(
                event.getStudentEmail(),
             event.getCompanyName(),
                event.getJobId(),
                event.getApplication_status());

    }
}