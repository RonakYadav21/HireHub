package com.CompanyService.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CompanyService.Messaging.JobStatusPublisher;
import com.CompanyService.Model.ApplicationResponseDTO;
import com.CompanyService.Model.Company;
import com.CompanyService.Model.CompanyDTO;
import com.CompanyService.Model.JobPosting;
import com.CompanyService.Model.JobPostingDTO;
import com.CompanyService.Model.StudentDTO;
import com.CompanyService.Repository.CompanyRepository;
import com.CompanyService.Repository.JobPostingRepository;
import com.CompanyService.service.ApplicationService;
import com.CompanyService.service.CompanyService;
import com.CompanyService.service.StudentFeignClient;
import com.CompanyService.Model.CompanyJobApplication;
import com.CompanyService.Repository.ApplicationRepo;
@RestController
@RequestMapping("/Company")
public class CompanyController {

	@Autowired
	 private CompanyService companyservice;
	@Autowired
	private PasswordEncoder passwordEncoder; 

	@Autowired
	  private  CompanyRepository  companyRepo;
	
	@Autowired
	private JobPostingRepository jobPostingRepo;
	
	  @Autowired
	    private StudentFeignClient studentFeignClient;
	  
	  @Autowired
	  private ApplicationRepo  applicationRepo;
	  @Autowired
	  private ApplicationService applicationService;
	  @Autowired
	    private  JobStatusPublisher publisher;

	
@PostMapping("/signup")
public ResponseEntity<?> companysignup (@RequestBody Company  company){
	company.setPassword(passwordEncoder.encode(company.getPassword()));

	Company response= companyservice.Register(company);
	return ResponseEntity.status(HttpStatus.CREATED).body(response);
}

@GetMapping("/viewprofile")
public ResponseEntity<?> viewProfile(@RequestHeader("X-User-Email") String email)  {
    CompanyDTO response = companyservice.CompanyProfile(email);
	return ResponseEntity.status(HttpStatus.OK).body(response);
}

    // logic to fetch company profile


@PostMapping("/addjob")
public JobPosting addJobPosting(@RequestBody JobPosting job,
                                @RequestHeader("X-User-Email") String email) {
    // Find company by email from JWT header
    Company company = companyRepo.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Company not found"));

    job.setCompany(company);
    return companyservice.saveJob(job);
}


@GetMapping("/jobs")
public ResponseEntity<List<JobPostingDTO>> getAllJobs() {
    List<JobPostingDTO> jobs = companyservice.getAllJobPostings();
    return ResponseEntity.ok(jobs);
}



@GetMapping("/search")
public List<JobPosting> searchJobs(
        @RequestParam(required = false) String companyName,
        @RequestParam(required = false) String domain,
        @RequestParam(required = false) String location,
        @RequestParam(required = false) Double minSalary) {

    return companyservice.searchJobs(companyName, domain, location, minSalary);
}

@PostMapping("jobs/{jobId}/notify-application")
public ResponseEntity<String> receiveJobApplication(
        @PathVariable Long jobId,
        @RequestParam String studentEmail) {

    // Fetch job
    JobPosting job = jobPostingRepo.findById(jobId)
            .orElseThrow(() -> new RuntimeException("Job not found"));

    // Fetch student
    StudentDTO student = studentFeignClient.getStudentByEmail(studentEmail);

    if (student == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Student not found with email: " + studentEmail);
    }

    if (student.getDomain() == null || job.getDomain() == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Missing domain information for student or job");
    }

    if (!student.getDomain().equalsIgnoreCase(job.getDomain())
            || (student.getCgpa() < job.getCgpa())) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Student not eligible");
    }
    // Save application
    CompanyJobApplication app = CompanyJobApplication.builder()
            .jobId(jobId)
            .studentEmail(student.getEmail())
            .resumeUrl(student.getResumeUrl())
            .status("PENDING")
            .build();
    applicationRepo.save(app);

    return ResponseEntity.ok("Application received");

}

@GetMapping("jobs/{jobId}")
public JobPostingDTO getJobById(@PathVariable Long jobId) {
    return companyservice.getJobById(jobId);
}

@GetMapping("/jobforcompany")
public List<JobPostingDTO> getAllForCompany(@RequestHeader("X-User-Email") String email) {
	    Company com=companyservice.getCompanyByEmail(email);
	    
	        List<JobPostingDTO> jobs= companyservice.getJobsCompanyId(com.getId());
	return jobs;
			
}
@PutMapping("Editjobs/{jobId}")
public ResponseEntity<String> editJobs(
        @PathVariable("jobId") Long jobId,
        @RequestBody JobPosting job) {

    companyservice.editJobs(jobId, job);
    return ResponseEntity.ok("Job updated successfully");
}

@GetMapping("/applications")
public ResponseEntity<List<ApplicationResponseDTO>> application(
        @RequestHeader("X-User-Email") String email) {

    List<ApplicationResponseDTO> response = applicationService.getApplicationsForCompany(email);
    return ResponseEntity.ok(response);
}



@PutMapping("/applications/{id}/accept")
public ResponseEntity<String> acceptApplication(@PathVariable Long id) {
    return applicationRepo.findById(id)
            .map(app -> {
                app.setStatus("ACCEPTED");
                applicationRepo.save(app);
                publisher.sendJobStatusUpdate(app.getJobId(), app.getStudentEmail(), "ACCEPTED"); // ✅ fixed
                return ResponseEntity.ok("Application accepted");
            })
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Application not found"));
}


@PutMapping("/applications/{id}/reject")
public ResponseEntity<?> rejectApplication(@PathVariable Long id) {
	  return applicationRepo.findById(id)
	            .map(app -> {
	                app.setStatus("REJECTED");
	                applicationRepo.save(app);
	                publisher.sendJobStatusUpdate(app.getJobId(), app.getStudentEmail(), "REJECTED"); // ✅ fixed
	                return ResponseEntity.ok("Application rejected");
	            })
	            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Application not found"));
	}

@GetMapping("/getAllCompany")
public ResponseEntity<List<CompanyDTO>> allCompnay(){
	return ResponseEntity.ok(companyservice.getAllCompany());
}

@GetMapping("/getAllJobs")
public ResponseEntity<List<CompanyJobApplication>> alljobs(){
	return ResponseEntity.ok(applicationService.getAllAppliedJobs());
}

@GetMapping("/jobcount")
public ResponseEntity<Long> getjobcount() {
    long count = jobPostingRepo.count();
    return ResponseEntity.ok(count);
}

@GetMapping("/count")
public ResponseEntity<Long> getcompanycount() {
    long count = companyservice.getCompanyCount();
    return ResponseEntity.ok(count);
}
}