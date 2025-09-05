package com.StudentService.Model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentProfileDto {

    private String name;
    private String image;
    private String gender;
    private Date dob;
    private String course;
    private String contact;
    private String email;
    private String address;
    private String department;
    private String batch;
    private String domain ;
    private double cgpa;
    private String resumeUrl;
    private String certifications;
    private String appliedDrives;
    private String internships;
    private int semester;
    private int backlogs;
    private String skills;
}
