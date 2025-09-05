package com.StudentService.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.StudentService.Model.JobPostingDTO;


@Service
public class JobService {

    @Autowired
    private PlacementFeignClient placementFeignClient;

    public List<JobPostingDTO> getAllJobListings() {
        return placementFeignClient.getAllJobsFromPlacement();
    }
    
    public List<JobPostingDTO> searchJobs(String companyName, String location, String domain, String minSalary) {
        return placementFeignClient.searchJobs(companyName, location, domain, minSalary);
    }
}