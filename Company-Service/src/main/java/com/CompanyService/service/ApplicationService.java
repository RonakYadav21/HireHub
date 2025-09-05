package com.CompanyService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CompanyService.Model.ApplicationResponseDTO;
import com.CompanyService.Model.Company;
import com.CompanyService.Model.CompanyJobApplication;
import com.CompanyService.Model.JobPosting;
import com.CompanyService.Repository.ApplicationRepo;
import com.CompanyService.Repository.CompanyRepository;
import com.CompanyService.Repository.JobPostingRepository;

@Service
public class ApplicationService {

    @Autowired
    private CompanyRepository companyRepo;

    @Autowired
    private JobPostingRepository jobPostingRepo;

    @Autowired
    private ApplicationRepo applicationRepo;

    public List<ApplicationResponseDTO> getApplicationsForCompany(String email) {
        // ✅ find company by email
        Company company = companyRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Company not found with email: " + email));

        // ✅ get all jobs of this company
        List<JobPosting> jobs = jobPostingRepo.findByCompany(company);

        if (jobs.isEmpty()) {
            return List.of(); // no jobs => no applications
        }

        List<Long> jobIds = jobs.stream().map(JobPosting::getId).toList();

        // ✅ get all applications for those jobs
        List<CompanyJobApplication> applications = applicationRepo.findByJobIdIn(jobIds);

        // ✅ map to DTO with job title
        return applications.stream().map(app -> {
            String jobTitle = jobs.stream()
                    .filter(j -> j.getId().equals(app.getJobId()))
                    .map(JobPosting::getJobTitle)
                    .findFirst()
                    .orElse("Unknown Job");

            return new ApplicationResponseDTO(
            		app.getId(),
                    app.getStudentEmail(),
                    app.getResumeUrl(),
                    app.getStatus(),
                    jobTitle
            );
        }).toList();
        
        
    }

	public List<CompanyJobApplication> getAllAppliedJobs() {
		 List<CompanyJobApplication> applications=applicationRepo.findAll();
				 return applications;
	}
    
    
}
