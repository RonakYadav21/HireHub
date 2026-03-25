package com.PlacementService.Dto;

import java.io.Serializable;
import java.time.LocalDateTime;

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
    private double minCgpa;

    private int numberOfPosts;
    private String selectionProcess;

    private LocalDateTime deadline;

    private String jobType;
private String skillrequired;     // Getters and Setters (or use Lombok if you like)
}