package com.Admin_Service.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyStatusEvent {
    private Long companyId;
    private String status; // PENDING, ACCEPTED, REJECTED
}
