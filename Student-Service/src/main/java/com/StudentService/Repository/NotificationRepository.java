package com.StudentService.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.StudentService.Model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByStudentEmailOrderByCreatedAtDesc(String studentEmail);

    List<Notification> findByStudentEmail(String studentEmail);
}