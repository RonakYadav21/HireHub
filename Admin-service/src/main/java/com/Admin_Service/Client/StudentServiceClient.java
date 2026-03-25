package com.Admin_Service.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

import com.Admin_Service.Model.StudentGraphDTO;
import com.Admin_Service.Model.StudentPlacement;

@FeignClient(name = "STUDENT-SERVICE")   // must match service name registered in Eureka
public interface StudentServiceClient {

    @GetMapping("/Student/allstudent-List")
    List<StudentPlacement> getAllStudents();

    @GetMapping("/Student/count")
    long countStudent();

    @GetMapping("/Student/getLatestStudent")
	List<StudentPlacement> getLatestStudents();

    @GetMapping("/Student/getStudentGraph")
	List<StudentGraphDTO> getStudentGraph();
}
