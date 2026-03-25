package com.StudentService.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobStatusEvent {

    private Long jobId;
    private String companyName;
    private String studentEmail;
    private String application_status; 
}
