package com.PlacementService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PlacementService.Model.JobApplication;

import java.util.List;
import java.util.Optional;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    boolean existsByStudentEmailAndJobId(String studentEmail, Long jobId);

    Optional<JobApplication> findByStudentEmailAndJobId(String studentEmail, Long jobId);

	List<JobApplication> findByStudentEmail(String studentEmail);
	long countByStatus(String string);

//    JobApplication findByStudentEmailAndJobId(String studentEmail, Long jobId);
	
}