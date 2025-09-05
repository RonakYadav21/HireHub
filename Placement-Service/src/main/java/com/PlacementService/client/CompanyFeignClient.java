package com.PlacementService.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.PlacementService.Dto.CompanyDTO;
import com.PlacementService.Dto.JobPostingDTO;
import com.PlacementService.Dto.appliedJobDTO;

@FeignClient("COMPANY-SERVICE")
public interface CompanyFeignClient {
    @GetMapping("/Company/jobs")
    List<JobPostingDTO> getAllJobs();
    
    @GetMapping("/Company/search")
    List<JobPostingDTO> searchJobs(
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String domain,
            @RequestParam(required = false) String minSalary
    );
    
//    @GetMapping("/company/jobs/{jobId}")
//    JobPostingDTO getJobById(@PathVariable Long jobId);

    @PostMapping("/Company/jobs/{jobId}/notify-application")
    void notifyCompanyAboutApplication(@PathVariable Long jobId, @RequestParam String studentEmail);
    @GetMapping("/Company/jobs/{jobId}")
    JobPostingDTO getJobById(@PathVariable("jobId") Long jobId);
    
    @GetMapping("/Company/getAllCompany")
    public List<CompanyDTO> allCompany();

  
    @GetMapping("/Company/getAllJobs")
 public List<appliedJobDTO> alljobs();

 @GetMapping("/Company/count")
	 public long countcompany();
 
 @GetMapping("/Company/jobcount")
 public long getjobcount();

}