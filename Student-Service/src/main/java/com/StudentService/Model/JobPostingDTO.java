package com.StudentService.Model;

import java.io.Serializable;


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
    private String domain;
    private double cgpa;

    // Getters and Setters
}