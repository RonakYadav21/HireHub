package com.PlacementService.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobStatusEvent {

	private String studentEmail;
	private String companyName;
    private Long jobId;
    private String application_status; // PENDING, ACCEPTED, REJECTED
}
