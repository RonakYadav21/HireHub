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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Admin_Service.AdminService.AdminService;
import com.Admin_Service.Messaging.companyStatusPublisher;
//import com.Admin_Service.Messaging.companyStatusPublisher;
import com.Admin_Service.Model.Admin;
import com.Admin_Service.Model.AdminDTO;
import com.Admin_Service.Model.CompanyDTO;
import com.Admin_Service.Model.CompanyStatusEvent;
import com.Admin_Service.Model.DashboardDTO;
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
	private companyStatusPublisher  publisher;
	@PostMapping("/signup")
	public ResponseEntity<?> companysignup (@RequestBody Admin admin){
		admin.setPassword(passwordEncoder.encode(admin.getPassword()));

		Admin response= adminservice.Register(admin);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/viewprofile")
	public ResponseEntity<?> viewProfile(@RequestHeader("X-User-Email") String email)  {
	    AdminDTO response = adminservice.AdminProfile(email);
		return ResponseEntity.status(HttpStatus.OK).body(response);
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
	
	@PutMapping("/accept")
	public ResponseEntity<String> acceptApplication(@RequestBody CompanyStatusEvent request) {
		 Long companyId = request.getCompanyId();
		    String email = request.getEmail();
		 publisher.sendcompanyStatusUpdate(companyId,email, "ACCEPTED"); // ✅ fixed
         return ResponseEntity.ok("Application accepted");
	}
	
	
	@PutMapping("/reject")
	public ResponseEntity<String> rejectApplication(@RequestBody CompanyStatusEvent request) {
		 Long companyId = request.getCompanyId();
		    String email = request.getEmail();
		 publisher.sendcompanyStatusUpdate(companyId,email, "REJECTED"); // ✅ fixed
         return ResponseEntity.ok("Application rejected");
	}
	
	@GetMapping("/dashboard")
	public DashboardDTO getDashboard() {
	    return adminservice.getDashboardData();
	}
	
	
}
