package com.CompanyService.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobPostingDTO {
	public JobPostingDTO(String jobTitle, Company company) {
	    this.jobTitle = jobTitle;
	    this.companyName = company != null ? company.getName() : null;
	}

	private Long id;
    private String jobTitle;
    private String description;
    private String location;
    private String salary;
    private String companyName;
    private String domain; // Assuming "domain" is part of your job posting
    private double cgpa;
    private int numberOfPosts;
    
}
