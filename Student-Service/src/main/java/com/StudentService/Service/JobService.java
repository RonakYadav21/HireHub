package com.StudentService.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.StudentService.Model.JobPostingDTO;
import com.StudentService.Model.Notification;
import com.StudentService.Repository.NotificationRepository;


@Service
public class JobService {

    @Autowired
    private PlacementFeignClient placementFeignClient;

    @Autowired
    private NotificationRepository notificationRepository;

    public List<JobPostingDTO> getAllJobListings(String studentEmail) {

        // 1️⃣ get all jobs from company service
        List<JobPostingDTO> jobs =
                placementFeignClient.getAllJobsFromPlacement();

        // 2️⃣ get notifications for this student
        List<Notification> notifications =
                notificationRepository.findByStudentEmail(studentEmail);

        // 3️⃣ map jobId -> status
        Map<Long, String> jobStatusMap = notifications.stream()
                .collect(Collectors.toMap(
                        Notification::getJobId,
                        Notification::getApplication_status,
                        (existing, replacement) -> replacement
                ));

        // 4️⃣ set status in DTO
        for (JobPostingDTO job : jobs) {

            String status = jobStatusMap.get(job.getId());

            if (status != null) {
                job.setApplication_status(status);
            } else {
                job.setApplication_status("NOT_APPLIED");
            }
        }

        return jobs;
    }


    public List<JobPostingDTO> searchJobs(String companyName,
                                          String location,
                                          String domain,
                                          String minSalary) {

        return placementFeignClient.searchJobs(
                companyName,
                location,
                domain,
                minSalary
        );
    }
}