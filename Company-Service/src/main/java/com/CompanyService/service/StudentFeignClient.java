package com.CompanyService.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.CompanyService.Model.StudentDTO;

@FeignClient(name = "STUDENT-SERVICE")
public interface StudentFeignClient {

    @GetMapping("/Student/students/by-email")
    StudentDTO getStudentByEmail(@RequestParam String email);
}