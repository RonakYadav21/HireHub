package com.Admin_Service.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class appliedJobDTO {
	
	    private Long id;
	  private  Long jobId;
  private String studentEmail;
  private String  resumeUrl;
  private String   status;
}