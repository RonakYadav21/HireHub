package com.PlacementService.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.PlacementService.Model.StudentPlacement;

@FeignClient(name = "STUDENT-SERVICE")
public interface StudentFeignClient {

    @GetMapping("/Student/allstudent-List")
        List<StudentPlacement>getAllStudents();
//    StudentDTO getStudentByEmail(@PathVariable String email);

    @GetMapping("/Student/count")
	 public long countStudent();
}
