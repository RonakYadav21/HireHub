package com.CompanyService.Model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public class Company {
		
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false)
	    private String name;

	    @Column(unique = true, nullable = false)
	    private String email;

	    @Column(nullable = false)
	    private String password;

//	    private String role;
        private String logo;
	    private String contactPerson;
	    private String phone;
	    private String website;
	    private String address;
	    private String industry;
	    private String domain; 
	    private boolean approved;
	    private String description;     // About the company

	    @CreationTimestamp
	    @Column(updatable = false, nullable = false)
	    private LocalDateTime createdAt;
	    // One-to-Many Relationship with JobPostings
	    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	    private List<JobPosting> jobPostings;

		
	}

