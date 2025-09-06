package com.PlacementService.integration;

import java.util.List;

import org.springframework.stereotype.Service;

import com.PlacementService.Dto.CompanyDTO;
import com.PlacementService.Dto.JobPostingDTO;
import com.PlacementService.Dto.appliedJobDTO;
import com.PlacementService.client.CompanyFeignClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class CompanyIntegrationService {

    private final CompanyFeignClient companyFeignClient;

    public CompanyIntegrationService(CompanyFeignClient companyFeignClient) {
        this.companyFeignClient = companyFeignClient;
    }

    // 1️⃣ Get All Jobs
    @Retry(name = "companyRetry", fallbackMethod = "fallbackJobs")
    @CircuitBreaker(name = "companyCircuitBreaker", fallbackMethod = "fallbackJobs")
    public List<JobPostingDTO> getAllJobs() {
        return companyFeignClient.getAllJobs();
    }

    public List<JobPostingDTO> fallbackJobs(Throwable t) {
        return List.of();
    }

    // 2️⃣ Search Jobs
    @Retry(name = "companyRetry", fallbackMethod = "fallbackJobs")
    @CircuitBreaker(name = "companyCircuitBreaker", fallbackMethod = "fallbackJobs")
    public List<JobPostingDTO> searchJobs(String companyName, String location, String domain, String minSalary) {
        return companyFeignClient.searchJobs(companyName, location, domain, minSalary);
    }

    // 3️⃣ Notify Company
    @Retry(name = "companyRetry", fallbackMethod = "fallbackNotify")
    @CircuitBreaker(name = "companyCircuitBreaker", fallbackMethod = "fallbackNotify")
    public void notifyCompanyAboutApplication(Long jobId, String studentEmail) {
        companyFeignClient.notifyCompanyAboutApplication(jobId, studentEmail);
    }

    public void fallbackNotify(Long jobId, String studentEmail, Throwable t) {
        System.out.println("⚠ Failed to notify company for jobId=" + jobId + ", student=" + studentEmail);
    }

    // 4️⃣ Get Job By Id
    @Retry(name = "companyRetry", fallbackMethod = "fallbackJobById")
    @CircuitBreaker(name = "companyCircuitBreaker", fallbackMethod = "fallbackJobById")
    public JobPostingDTO getJobById(Long jobId) {
        return companyFeignClient.getJobById(jobId);
    }

    public JobPostingDTO fallbackJobById(Long jobId, Throwable t) {
        return null;
    }

    // 5️⃣ Get All Companies
    @Retry(name = "companyRetry", fallbackMethod = "fallbackCompanies")
    @CircuitBreaker(name = "companyCircuitBreaker", fallbackMethod = "fallbackCompanies")
    public List<CompanyDTO> allCompany() {
        return companyFeignClient.allCompany();
    }

    public List<CompanyDTO> fallbackCompanies(Throwable t) {
        return List.of();
    }

    // 6️⃣ Get All Jobs (Applied Jobs List)
    @Retry(name = "companyRetry", fallbackMethod = "fallbackAppliedJobs")
    @CircuitBreaker(name = "companyCircuitBreaker", fallbackMethod = "fallbackAppliedJobs")
    public List<appliedJobDTO> alljobs() {
        return companyFeignClient.alljobs();
    }

    public List<appliedJobDTO> fallbackAppliedJobs(Throwable t) {
        return List.of();
    }

    // Extra: Company Count
    @Retry(name = "companyRetry", fallbackMethod = "fallbackCompanyCount")
    @CircuitBreaker(name = "companyCircuitBreaker", fallbackMethod = "fallbackCompanyCount")
    public long countcompany() {
        return companyFeignClient.countcompany();
    }

    public long fallbackCompanyCount(Throwable t) {
        return -1;
    }

    // Extra: Job Count
    @Retry(name = "companyRetry", fallbackMethod = "fallbackJobCount")
    @CircuitBreaker(name = "companyCircuitBreaker", fallbackMethod = "fallbackJobCount")
    public long getjobcount() {
        return companyFeignClient.getjobcount();
    }

    public long fallbackJobCount(Throwable t) {
        return -1;
    }
}
