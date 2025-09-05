package com.StudentService.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.StudentService.Model.RegisterAuthUserRequest;
import com.StudentService.Model.Student;
import com.StudentService.Model.StudentProfileDto;
import com.StudentService.Repository.StudentRepo;

@Service
public class StudentService {
	@Autowired
	StudentRepo studentrepo;
	
	@Autowired
	AuthServiceClient authServiceClient;
	
    @Autowired
    private StudentMapper studentMapper;
	
  public Student register(Student student) {
	  // Save to local student DB
	Student s= studentrepo.save(student);
	
	
	
	 // Prepare AuthService DTO
    RegisterAuthUserRequest authUser = new RegisterAuthUserRequest();
    authUser.setUsername(student.getEmail());
    authUser.setPassword(student.getPassword()); // raw or encoded
    authUser.setRole("ROLE_STUDENT");

    // Call AuthService using Feign
    authServiceClient.registerUser(authUser);
	
	return s;
	  }

public StudentProfileDto viewStudent(String email) {
	   Student student = studentrepo.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("Student not found"));

	   return studentMapper.studentToStudentProfileDto(student);

}

public List<Student> getAllStudent() {
	 List<Student> Allstudent =studentrepo.findAll();
	return Allstudent;
}

  public long getStudentCount() {
        return studentrepo.count();
    }}