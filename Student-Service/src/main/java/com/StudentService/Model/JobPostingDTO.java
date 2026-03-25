package com.StudentService.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobPostingDTO implements Serializable {
    private Long id;
    private String jobTitle;
    private String description;
    private String location;
    private String salary;
    private String companyName;
    private String domain; // Assuming "domain" is part of your job posting
    private int numberOfPosts;
    private String selectionProcess;
    private LocalDateTime deadline;
    private double minCgpa;
    private String jobType;
    private String skillrequired;    
    private String application_status;

}