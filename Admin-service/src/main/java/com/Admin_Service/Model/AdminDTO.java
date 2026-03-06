package com.Admin_Service.Model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {
	 private String fullName;

	    @Column(unique = true, nullable = false)
	    private String email;
	    private String phoneNumber;

	    private String role; // system controlled

	    private String status;   // or "PENDING"

}
