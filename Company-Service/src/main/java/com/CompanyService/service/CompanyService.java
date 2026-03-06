package com.CompanyService.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.CompanyService.Messaging.ActivityPublisher;
import com.CompanyService.Model.ApplicationResponseDTO;
import com.CompanyService.Model.Company;
import com.CompanyService.Model.CompanyDTO;
import com.CompanyService.Model.JobPosting;
import com.CompanyService.Model.JobPostingDTO;
import com.CompanyService.Model.RegisterAuthUserRequest;
import com.CompanyService.Repository.CompanyRepository;
import com.CompanyService.Repository.JobPostingRepository;

@Service
public class CompanyService {
	
	@Autowired
	private JobPostingRepository jobPostingRepo;
	
	@Autowired
	private JobPostingMapper mapper;
	
//	@Autowired
//	CompanyRepository repo;
	@Autowired
	private JobPostingRepository jobRepo;
	
	@Autowired  
	AuthServiceClient authServiceClient;
	
	@Autowired
	CompanyRepository companyRepository;
	@Autowired
	private ActivityPublisher activityPublisher;
	@Autowired
	  CompanyMapper companyMapper;
	public Company Register(Company company) {
	    company.setStatus("PENDING");;  // Optional: Wait for admin approval
	    company.setCreatedAt(LocalDateTime.now());
	    
	    Company savedCompany = companyRepository.save(company);

	    RegisterAuthUserRequest authUser = new RegisterAuthUserRequest();
	    authUser.setUsername(company.getEmail());
	    authUser.setPassword(company.getPassword());
	    authUser.setRole("ROLE_COMPANY");
	    authServiceClient.registerUser(authUser);
	    activityPublisher.publishActivity(
	            "COMPANY_REGISTERED",
	            "New company registered: " + company.getName()
	    );
	    return savedCompany;
	}

	public JobPosting saveJob(JobPosting job) {
		 activityPublisher.publishActivity(
			        "JOB_POSTED",
		            job.getCompany().getName()+" posted a new job for " + job.getJobTitle()
		    );
	    return jobRepo.save(job);
	}


	public List<JobPostingDTO> getAllJobPostings() {
	    return mapper.toDtoList(jobPostingRepo.findAll());
	}




	    public List<JobPosting> searchJobs(String companyName, String domain, String location, Double minSalary) {
	        Specification<JobPosting> spec = Specification
	                .where(JobPostingSpecification.hasCompanyName(companyName))
	                .and(JobPostingSpecification.hasDomain(domain))
	                .and(JobPostingSpecification.hasLocation(location))
	                .and(JobPostingSpecification.salaryGreaterThanOrEqual(minSalary));

	        return jobPostingRepo.findAll(spec);
	    }
	    
	    
	    public JobPostingDTO getJobById(Long jobId) {
	        JobPosting job = jobPostingRepo.findById(jobId)
	                .orElseThrow(() -> new RuntimeException("Job not found with id: " + jobId));

	        return new JobPostingDTO(job.getJobTitle(), job.getCompany());
	    }

	    public CompanyDTO CompanyProfile(String email) {
	        Company company = companyRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Company not found"));

	            return companyMapper.companyToCompanyDto(company);
	        }

		

		public Company getCompanyByEmail(String email) {
	        Company company = companyRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Student not found"));
			return company;
		}

	
		  public List<JobPostingDTO> getJobsCompanyId(Long companyId) {
		        List<JobPosting> jobs = jobPostingRepo.findByCompanyId(companyId);
		        return mapper.toDtoList(jobs);
		    }

		  public JobPosting editJobs(Long id, JobPosting updatedJob) {
			    return jobPostingRepo.findById(id).map(existingJob -> {
			        // update fields
			        existingJob.setJobTitle(updatedJob.getJobTitle());
			        existingJob.setDescription(updatedJob.getDescription());
			        existingJob.setLocation(updatedJob.getLocation());
			        existingJob.setSalary(updatedJob.getSalary());
			        existingJob.setDomain(updatedJob.getDomain());
			        existingJob.setCgpa(updatedJob.getCgpa());
			        existingJob.setNumberOfPosts(updatedJob.getNumberOfPosts());

			        // save and return updated entity
			        return jobPostingRepo.save(existingJob);
			    }).orElseThrow(() -> new RuntimeException("Job not found with id: " + id));
			}

		public List<CompanyDTO> getAllCompany() {
			List<Company> allcompanies=companyRepository.findAll();
			 List<CompanyDTO> companyDTOs = allcompanies.stream()
				        .map(company -> companyMapper.companyToCompanyDto(company))
				        .collect(Collectors.toList());
				    return companyDTOs;
				}

		  public long getCompanyCount() {
		        return companyRepository.count();
		    }
		  public List<CompanyDTO> getPendingCompanies() {

		        List<Company> companies = companyRepository.findByStatus("PENDING");

		        return companies.stream()
		                .map(companyMapper::companyToCompanyDto)
		                .toList();
		    }

		
	    }

	
	



