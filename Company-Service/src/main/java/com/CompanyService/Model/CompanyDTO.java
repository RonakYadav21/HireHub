package com.CompanyService.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
    private Long id;                // Unique company ID
    private String name;            // Company name
    private String email;           // Login / contact email
    private String phone;   // HR or recruiter phone
    private String address;         // Office address or HQ
    private String industry;        // Industry type (IT, Finance, etc.)
    private String website;         // Official website
    private String description;     // About the company
    private String logo;         // Company logo (stored in DB / cloud storage)
    private String contactPerson;
    private String domain; 

    // Placement-specific info
 
    private String status ;          // e.g., Active, Pending, Blacklisted
}
