package com.Admin_Service.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Admin_Service.AdminService.AdminService;
import com.Admin_Service.Messaging.JobStatusPublisher;
import com.Admin_Service.Model.Admin;
import com.Admin_Service.Model.CompanyDTO;
import com.Admin_Service.Model.JobPostingDTO;
import com.Admin_Service.Model.StudentPlacement;
import com.Admin_Service.Model.appliedJobDTO;


@RestController
@RequestMapping("/Admin")
public class AdminController {

	@Autowired
	 private AdminService adminservice;
	@Autowired
	private PasswordEncoder passwordEncoder; 

	@Autowired
	private JobStatusPublisher  publisher;
	@PostMapping("/signup")
	public ResponseEntity<?> companysignup (@RequestBody Admin admin){
		admin.setPassword(passwordEncoder.encode(admin.getPassword()));

		Admin response= adminservice.Register(admin);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/Studentcount")
	public ResponseEntity<Long> getStudentCount() {
		return ResponseEntity.ok(adminservice.countStudent());
	}
	
	@GetMapping("/getStudentList")
	public ResponseEntity<List<StudentPlacement>> getallstudent(){
	    return ResponseEntity.ok(adminservice.fetchAllStudents());

	}
	
	@GetMapping("/getAllCompany")
	public ResponseEntity<List<CompanyDTO>> getallcompany(){
	    return ResponseEntity.ok(adminservice.fetchAllCompnay());

	}
	
	@GetMapping("/Companycount")
	public ResponseEntity<Long> getCompanyCount() {
		return ResponseEntity.ok(adminservice.countcompany());
	}

	@GetMapping("/jobscount")
	public ResponseEntity<Long> getjobCount() {
		return ResponseEntity.ok(adminservice.countjobs());
	}
	
	@GetMapping("/getAllJobs")
	public ResponseEntity<List<JobPostingDTO>> alljobs(){
	    return ResponseEntity.ok(adminservice.fetchAllJobs());

	}
	
	@PutMapping("/accept/{companyId}")
	public ResponseEntity<String> acceptApplication(@PathVariable Long companyId) {
		 publisher.sendJobStatusUpdate(companyId, "ACCEPTED"); // ✅ fixed
         return ResponseEntity.ok("Application accepted");
	}
	
	
	@PutMapping("/reject/{companyId}")
	public ResponseEntity<String> rejectApplication(@PathVariable Long companyId) {
		 publisher.sendJobStatusUpdate(companyId, "REJECTED"); // ✅ fixed
         return ResponseEntity.ok("Application rejected");
	}
}
