package com.CompanyService.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponseDTO {
	private Long Id;
    private String studentEmail;
    private String resumeUrl;
    private String status;
    private String jobTitle;
}
