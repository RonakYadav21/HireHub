package com.StudentService.Service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.StudentService.Model.Notification;
import com.StudentService.Repository.NotificationRepository;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void createNotification(String email, String companyName , Long jobId, String status) {

        Notification notification = new Notification();

        notification.setStudentEmail(email);
        notification.setJobId(jobId);
        notification.setApplication_status(status);
        notification.setCompanyName(companyName);
        notification.setMessage(
            "Your application for  " + companyName + " is " + status
        );

        notificationRepository.save(notification);
    }

    public List<Notification> getNotifications(String email) {
        return notificationRepository
                .findByStudentEmailOrderByCreatedAtDesc(email);
    }

    public void markAsRead(Long id) {

        Notification n = notificationRepository.findById(id).orElseThrow();

        n.setRead(true);

        notificationRepository.save(n);
    }
}