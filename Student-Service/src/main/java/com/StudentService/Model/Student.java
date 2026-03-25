package com.StudentService.Model;

import java.sql.Date;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
	@Table(name = "students")
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public class Student {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String name;
	       
	    private String  Password;
	    private String image;
	 
	    private String gender;
	    
	    private Date dob;
	      
	    private String course;


	    private String contact;
	
	    @Column(name = "email", nullable = false, unique = true)
	    private String email;

	    private String  address;
	    
	    private String  department ;
	    private String batch ;
	    private String domain ;

	    private double cgpa ;
	    private String resume_url; 
//	    private String  certifications;
//	    private String applied_drives ;
	    private String role;
	    private String internships ;
	    private int semester ;
	     private int backlogs ;
	     private String skills ;
	     @CreationTimestamp
	     @Column(updatable = false)
	     private LocalDateTime createdAt;

}
