package com.PlacementService.Dto;

import java.io.Serializable;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Table(name = "students")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobPostingDTO implements Serializable {
    private Long id;
    private String jobTitle;
    private String description;
    private String location;
    private String salary;
    private String companyName; // optional, for display
    private String domain;
    private double cgpa; 
    // Getters and Setters (or use Lombok if you like)
}