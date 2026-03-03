package com.StudentService.Controller;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.StudentService.Model.JobPostingDTO;
import com.StudentService.Model.Student;
import com.StudentService.Model.StudentProfileDto;
import com.StudentService.Repository.StudentRepo;
import com.StudentService.Service.JobService;
import com.StudentService.Service.StudentMapper;
//import com.StudentService.Service.StudentService;
import com.StudentService.Service.StudentService;
@RestController
@RequestMapping("/Student")
public class StudentController {
	@Autowired
	private JobService jobService;
	
	@Autowired
	 private StudentService studentservice;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	 private StudentRepo studentRepository;
	@Autowired
	private StudentMapper studentMapper;
	
	@PostMapping("/signup")
	public ResponseEntity<?> studentsignup (@RequestBody Student  Student){
	    Student.setPassword(passwordEncoder.encode(Student.getPassword()));

	 Student response= studentservice.register(Student);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
		
@GetMapping("/allstudent-List")	
public List<Student> getAllStudent(){
	List<Student> allStudent=studentservice.getAllStudent();
	return  allStudent;
}
//	@PreAuthorize("hasRole('ROLE_STUDENT')")
	@PostMapping("/viewprofile")
	public ResponseEntity<?> viewStudent (@RequestHeader("X-User-Email") String email){		
		
    StudentProfileDto response=studentservice.viewStudent(email);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);}

	    @GetMapping("/view-jobs")
	    public ResponseEntity<List<JobPostingDTO>> viewJobs() {
	        return ResponseEntity.ok(jobService.getAllJobListings());
	    }
	    
	    @PutMapping("/updateprofile")
	    public ResponseEntity<?> updateProfile(
	            @RequestHeader("X-User-Email") String email,
	            @RequestBody StudentProfileDto dto) {

	        Student student = studentRepository.findByEmail(email)
	                .orElseThrow(() -> new RuntimeException("Student not found"));

	        studentMapper.updateStudentFromDto(dto, student);

	        studentRepository.save(student);

	        return ResponseEntity.ok(studentMapper.toDto(student));
	    }

	

	    
	    @GetMapping("/search")
	    public List<JobPostingDTO> searchJobs(
	            @RequestParam(required = false) String companyName,
	            @RequestParam(required = false) String location,
	            @RequestParam(required = false) String domain,
	            @RequestParam(required = false) String minSalary
	    ) {
	        return jobService.searchJobs(companyName, location, domain, minSalary);
	    }
	    
	    @GetMapping("/students/by-email")
	    public StudentProfileDto getStudentByEmail(@RequestParam String email) {
	        Student student = studentRepository.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("Student not found"));

	        return studentMapper.studentToStudentProfileDto(student);
	        

	    }
	    @GetMapping("/count")
	    public ResponseEntity<Long> getStudentCount() {
	        long count = studentservice.getStudentCount();
	        return ResponseEntity.ok(count);
	    }
	    
	    }
