package com.PlacementService.Controller;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.PlacementService.Dto.CompanyDTO;
import com.PlacementService.Dto.JobApplicationDTO;
import com.PlacementService.Dto.JobApplicationRequest;
import com.PlacementService.Dto.JobPostingDTO;
import com.PlacementService.Dto.appliedJobDTO;
//import com.PlacementService.Model.JobApplication;
import com.PlacementService.Model.PlacementOfficer;
import com.PlacementService.Model.StudentPlacement;
import com.PlacementService.jobService.JobService;
import com.PlacementService.jobService.PlacementOfficerService;

@RestController
@RequestMapping("/Placement")
public class PlacementController {

	@Autowired
	private JobService jobservice;
	
	@Autowired
	 private PlacementOfficerService officerService;
	@PostMapping("/signup")
	  public ResponseEntity<?> signupTPO(@RequestBody PlacementOfficer officer) {
        PlacementOfficer saved = officerService.signupTPO(officer);
        return ResponseEntity.ok("TPO registered successfully. Pending admin approval.");
    }
	
	@GetMapping("/jobs")
	public ResponseEntity<List<JobPostingDTO>> getJobsForStudents() {
	    return ResponseEntity.ok(jobservice.fetchAllJobsForStudents());
	}


	
	
	  @GetMapping("/search")
	    public List<JobPostingDTO> searchJobs(
	            @RequestParam(required = false) String companyName,
	            @RequestParam(required = false) String location,
	            @RequestParam(required = false) String domain,
	            @RequestParam(required = false) String minSalary
	    ) {
	        return jobservice.searchJobs(companyName, location, domain, minSalary);
	    }
	  
	  @PostMapping("/apply")
	  public String applyForJob(
			  @RequestBody JobApplicationRequest request, @RequestHeader("X-User-Role") String role,
	          @RequestHeader("X-User-Email") String studentEmail) throws AccessDeniedException {
		  if (!"ROLE_STUDENT".equals(role)) {
		        throw new AccessDeniedException("Only students can apply for jobs.");
		    }
		  return jobservice.applyForJob(studentEmail, request);
}
	  
@GetMapping("/applied-jobs")
public List<JobApplicationDTO> appliedJobs( @RequestHeader("X-User-Role") String role,
	          @RequestHeader("X-User-Email") String studentEmail) throws AccessDeniedException {
	  if (!"ROLE_STUDENT".equals(role)) {
	        throw new AccessDeniedException("Only students can access.");
	    }
	return jobservice.appliedJobList(studentEmail);
}

@GetMapping("/getStudentList")
public ResponseEntity<List<StudentPlacement>> getallstudent(){
    return ResponseEntity.ok(officerService.fetchAllStudents());

}
@GetMapping("/getAllCompany")
public ResponseEntity<List<CompanyDTO>> getallcompany(){
    return ResponseEntity.ok(officerService.fetchAllCompnay());

}

@GetMapping("/getAllJobs")
public ResponseEntity<List<appliedJobDTO>>alljobs(){
    return ResponseEntity.ok(officerService.fetchAllApploedJobs());

}
@GetMapping("/Studentcount")
public ResponseEntity<Long> getStudentCount() {
	return ResponseEntity.ok(officerService.countStudent());
}

@GetMapping("/Companycount")
public ResponseEntity<Long> getCompanyCount() {
	return ResponseEntity.ok(officerService.countcompany());
}

@GetMapping("/jobscount")
public ResponseEntity<Long> getjobCount() {
	return ResponseEntity.ok(officerService.countjobs());
}

@GetMapping("/placedStudentcount")
public ResponseEntity<Long> getplacedStudentCount() {
	return  ResponseEntity.ok(jobservice.getplacedStudent());
}
}
