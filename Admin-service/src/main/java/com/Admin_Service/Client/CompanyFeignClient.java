package com.Admin_Service.Client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.Admin_Service.Model.CompanyDTO;
import com.Admin_Service.Model.JobPostingDTO;
import com.Admin_Service.Model.appliedJobDTO;



@FeignClient("COMPANY-SERVICE")
public interface CompanyFeignClient {
//    @GetMapping("/Company/jobs")
//    List<JobPostingDTO> getAllJobs();

	  @GetMapping("/Company/getAllCompany")
	    public List<CompanyDTO> allCompany();

	  
	    @GetMapping("/Company/alljobs")
	 public List<JobPostingDTO> alljobs();

	 @GetMapping("/Company/count")
		 public long countcompany();
	 
	 @GetMapping("/Company/jobcount")
	 public long getjobcount();

	 @GetMapping("/Company/getPendingRequest")
	public List<CompanyDTO> getPendingCompanies();

}