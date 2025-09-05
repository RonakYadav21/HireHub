package com.PlacementService.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentPlacement {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long Studentid;
    // Candidate details
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    // Job/Internship details
    @Column(nullable = false)
    private String skills;

    @Column(nullable = false)
    private String domain;


    // Placement details
    @Column(nullable = false)
    private double cgpa;

    private String applied_drives;  // e.g., "Placed", "Pending", "Rejected"

   
}
