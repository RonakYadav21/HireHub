package com.StudentService.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.StudentService.Model.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {

	  Optional<Student> findByEmail(String email);	
		List<Student> findTop5ByOrderByCreatedAtDesc();

}
