package com.StudentService.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.StudentService.Model.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {

	  Optional<Student> findByEmail(String email);	
		List<Student> findTop5ByOrderByCreatedAtDesc();
		@Query("""
				SELECT MONTH(s.createdAt), COUNT(s)
				FROM Student s
				GROUP BY MONTH(s.createdAt)
				ORDER BY MONTH(s.createdAt)
				""")
				List<Object[]> getStudentRegistrationStats();

}
