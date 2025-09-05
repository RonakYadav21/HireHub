package com.CompanyService.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobStatusEvent {
    private Long jobId;
    private String studentEmail;
    private String status; // PENDING, ACCEPTED, REJECTED
}
