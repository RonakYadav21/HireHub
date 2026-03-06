package com.Admin_Service.Messaging;


import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.Admin_Service.Model.Activity;
import com.Admin_Service.Model.ActivityEvent;
import com.Admin_Service.Repository.ActivityRepository;


@Service
public class ActivityListener {

    private final ActivityRepository activityRepository;

    public ActivityListener(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @RabbitListener(queues = "activity-queue")
    public void receiveActivity(ActivityEvent event) {

        System.out.println("Received Activity Event: " + event);

        Activity activity = new Activity();
        activity.setType(event.getType());
        activity.setMessage(event.getMessage());
        activity.setTime(LocalDateTime.now());

        activityRepository.save(activity);

        System.out.println("Activity saved successfully.");
    }
}