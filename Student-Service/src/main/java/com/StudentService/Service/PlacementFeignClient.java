package com.StudentService.Service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.StudentService.Model.JobPostingDTO;


@FeignClient(name = "PLACEMENT-SERVICE")
public interface PlacementFeignClient {

    @GetMapping("/Placement/jobs")
    List<JobPostingDTO> getAllJobsFromPlacement();

@GetMapping("/Placement/search")
List<JobPostingDTO> searchJobs(
        @RequestParam(required = false) String companyName,
        @RequestParam(required = false) String location,
        @RequestParam(required = false) String domain,
        @RequestParam(required = false) String minSalary
);
}