package com.Admin_Service.Model;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDTO {

    private long students;
    private long companies;
    private long jobs;

    private List<StudentPlacement> latestStudents;
    private List<CompanyDTO> pendingCompanies;
    List<StudentGraphDTO> graph ;
    private List<Activity> activities;
}