package com.PlacementService.jobService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PlacementService.Dto.JobApplicationDTO;
import com.PlacementService.Dto.JobApplicationRequest;
import com.PlacementService.Dto.JobPostingDTO;
import com.PlacementService.Model.JobApplication;
import com.PlacementService.Repository.JobApplicationRepository;
import com.PlacementService.client.CompanyFeignClient;
import com.PlacementService.integration.CompanyIntegrationService;

@Service
public class JobService {
	  @Autowired
	    private CompanyIntegrationService companyIntegrationService;

    
    @Autowired
     private  JobApplicationRepository jobapplicationrepo;

//    @Cacheable("allJobs")  // You can later plug in Redis or Caffeine
    public List<JobPostingDTO> fetchAllJobsForStudents() {
        return companyIntegrationService.getAllJobs();
    }
    
    public List<JobPostingDTO> searchJobs(String companyName, String location, String domain, String minSalary) {
        return companyIntegrationService.searchJobs(companyName, location, domain, minSalary);
    }
    
    public String applyForJob(String studentEmail, JobApplicationRequest request) {
        // Check if already applied
        if (jobapplicationrepo.existsByStudentEmailAndJobId(studentEmail, request.getJobId())) {
            return "You have already applied for this job.";
        }

        try {
            // First notify company to check eligibility
        	companyIntegrationService.notifyCompanyAboutApplication(request.getJobId(), studentEmail);

            // If no exception -> student eligible -> save application
            JobApplication application = JobApplication.builder()
                    .studentEmail(studentEmail)
                    .jobId(request.getJobId())
                    .resumeUrl(request.getResumeUrl())
                    .status("PENDING")
                    .build();

            jobapplicationrepo.save(application);
            return "Job application submitted successfully.";

        } catch (Exception ex) {
            // If company rejects -> don’t save in DB
            return "Application failed: you are not eligible for this job" ;
        }
    }

    public List<JobApplicationDTO> appliedJobList(String studentEmail) {
        List<JobApplication> applications = jobapplicationrepo.findByStudentEmail(studentEmail);

        return applications.stream().map(app -> {
            JobPostingDTO job = companyIntegrationService.getJobById(app.getJobId());

            return new JobApplicationDTO(
                app.getJobId(),
                job.getJobTitle(),
                job.getCompanyName(),
                app.getStatus(),            // ✅ use getter, not setter   //    app.getStatus().name(),     // ✅ convert enum → String
                app.getCreatedAt()
            );
        }).collect(Collectors.toList());  // ✅ use collect for Java 8–15
    }

	public long getplacedStudent() {
	    return jobapplicationrepo.countByStatus("ACCEPTED");

	}

}