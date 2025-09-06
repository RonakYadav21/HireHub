package com.PlacementService.integration;

import java.util.List;

import org.springframework.stereotype.Service;

import com.PlacementService.Model.StudentPlacement;
import com.PlacementService.client.StudentFeignClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class StudentIntegrationService {

    private final StudentFeignClient studentFeignClient;

    public StudentIntegrationService(StudentFeignClient studentFeignClient) {
        this.studentFeignClient = studentFeignClient;
    }

    @Retry(name = "studentRetry", fallbackMethod = "fallbackStudents")
    @CircuitBreaker(name = "studentCircuitBreaker", fallbackMethod = "fallbackStudents")
    public List<StudentPlacement> getAllStudents() {
        return studentFeignClient.getAllStudents();
    }

    public List<StudentPlacement> fallbackStudents(Throwable t) {
        return List.of();
    }

    @Retry(name = "studentRetry", fallbackMethod = "fallbackStudentCount")
    @CircuitBreaker(name = "studentCircuitBreaker", fallbackMethod = "fallbackStudentCount")
    public long countStudents() {
        return studentFeignClient.countStudent();
    }

    public long fallbackStudentCount(Throwable t) {
        return -1;
    }
}
