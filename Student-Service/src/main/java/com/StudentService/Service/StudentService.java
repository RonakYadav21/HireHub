package com.StudentService.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import com.StudentService.Messaging.StudentEventPublisher;
import com.StudentService.Model.RegisterAuthUserRequest;
import com.StudentService.Model.Student;
import com.StudentService.Model.StudentGraphDTO;
import com.StudentService.Model.StudentProfileDto;
import com.StudentService.Repository.StudentRepo;

@Service
public class StudentService {
	@Autowired
	StudentRepo studentRepository;
	
	@Autowired
	AuthServiceClient authServiceClient;
	
    @Autowired
    private StudentMapper studentMapper;


    @Autowired
   private StudentEventPublisher activityPublisher;
	
  public Student register(Student student) {
	  // Save to local student DB
	  student.setRole("ROLE_STUDENT");
	Student s= studentRepository.save(student);
	
	 // Prepare AuthService DTO
    RegisterAuthUserRequest authUser = new RegisterAuthUserRequest();
    authUser.setUsername(student.getEmail());
    authUser.setPassword(student.getPassword()); // raw or encoded
    authUser.setRole("ROLE_STUDENT");
    // Call AuthService using Feign
    authServiceClient.registerUser(authUser);
    
    activityPublisher.publishActivity(
    		"STUDENT_REGISTERED",
    		student.getName()+" Registered" 
    		);
	
	return s;
	  }

public StudentProfileDto viewStudent(String email) {
	   Student student = studentRepository.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("Student not found"));

	   return studentMapper.studentToStudentProfileDto(student);

}

public List<Student> getAllStudent() {
	 List<Student> Allstudent =studentRepository.findAll();
	return Allstudent;
}

  public long getStudentCount() {
        return studentRepository.count();
    }

  public List<Student> getLatestStudents() {
      return studentRepository.findTop5ByOrderByCreatedAtDesc();
  }
  
  
  public List<StudentGraphDTO> getStudentRegistrationGraph() {

	    List<Object[]> data = studentRepository.getStudentRegistrationStats();

	    return data.stream().map(obj -> {

	        int monthNumber = (Integer) obj[0];
	        Long count = (Long) obj[1];

	        String monthName = Month.of(monthNumber)
	                                .getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

	        return new StudentGraphDTO(monthName, count);

	    }).toList();
	}
}